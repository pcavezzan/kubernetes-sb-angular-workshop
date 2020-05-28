package gin

import (
	"bytes"
	"github.com/pcavezzan/kubernetes/workshop/store/clustering"
	"github.com/pcavezzan/kubernetes/workshop/store/messages"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
	"net/http"
	"net/http/httptest"
	"net/url"
	"testing"
)

// ========= Mocking Store =============
type mockStore struct {
	mock.Mock
}

func (m *mockStore) Get(key string) (messages.Message, error) {
	args := m.Called(key)
	return args.Get(0).(messages.Message), args.Error(1)
}
func (m *mockStore) Put(message messages.Message) error {
	args := m.Called(message)
	return args.Error(0)
}

func TestNewMainRouter(t *testing.T) {

	store := mockStore{}
	topology := clustering.Topology{
		Current: "store-test",
		Members: nil,
	}

	router := NewMainRouter(&store, &topology)

	t.Run("Should create GET /messages/:key routes", func(t *testing.T) {
		routes := router.Routes()

		match := false
		for _, route := range routes {
			if route.Method == "GET" && route.Path == "/messages/:key" {
				match = true
				break
			}
		}
		assert.True(t, match)
	})

	t.Run("Should create PUT /messages/:key routes", func(t *testing.T) {
		routes := router.Routes()

		match := false
		for _, route := range routes {
			if route.Method == "PUT" && route.Path == "/messages/:key" {
				match = true
				break
			}
		}
		assert.True(t, match)
	})

	t.Run("Should create GET /ping routes", func(t *testing.T) {
		routes := router.Routes()

		match := false
		for _, route := range routes {
			if route.Method == "GET" && route.Path == "/ping" {
				match = true
				break
			}
		}
		assert.True(t, match)
	})

	t.Run("Should handle GET /message/:key", func(t *testing.T) {
		store.On("Get", "key").Return(messages.Message{"key", "value"}, nil)

		assert.HTTPBodyContains(t, router.ServeHTTP, "GET", "/messages/key", url.Values{}, "{\"key\":\"key\",\"value\":\"value\"}")
	})

	t.Run("Should handle PUT /message/:key", func(t *testing.T) {
		store.On("Put", messages.Message{"key", "value"}).Return(nil)
		w := httptest.NewRecorder()
		req, _ := http.NewRequest("PUT", "/messages/key", bytes.NewReader([]byte("{\"key\":\"key\",\"value\":\"value\"}")))

		router.ServeHTTP(w, req)

		assert.Equal(t, 201, w.Code)
	})

	t.Run("Should handle GET /ping", func(t *testing.T) {
		assert.HTTPSuccess(t, router.ServeHTTP, "GET", "/ping", url.Values{})
	})

}
