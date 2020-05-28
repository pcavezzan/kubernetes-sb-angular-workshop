package messages

import (
	"bytes"
	"github.com/dgraph-io/badger/v2"
	"github.com/gin-gonic/gin"
	"github.com/pcavezzan/kubernetes/workshop/store/clustering"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
	"net/http"
	"net/http/httptest"
	"testing"
)

// ========= Mocking Store =============
type mockStore struct {
	mock.Mock
}

func (m *mockStore) Get(key string) (Message, error) {
	args := m.Called(key)
	return args.Get(0).(Message), args.Error(1)
}
func (m *mockStore) Put(message Message) error {
	args := m.Called(message)
	return args.Error(0)
}

// ========= Mocking Topology =============
type mockMember struct {
	mock.Mock
}

func (m *mockMember) Id() string {
	args := m.Called()
	return args.String()
}
func (m *mockMember) Get(key string) string {
	args := m.Called(key)
	return args.String(0)
}

func TestEndPoint_Get(t *testing.T) {
	mockStore := mockStore{}

	ep := EndPoint{
		Store: &mockStore,
		Topology: &clustering.Topology{
			Current: "test",
			Members: nil,
		},
	}
	router := gin.Default()
	router.GET("/:key", ep.Get)

	t.Run("Should return value from store when key exists", func(t *testing.T) {
		mockStore.On("Get", "key").Return(Message{"key", "value"}, nil)
		w := httptest.NewRecorder()
		req, _ := http.NewRequest("GET", "/key", nil)

		router.ServeHTTP(w, req)

		assert.Equal(t, 200, w.Code)
		assert.Equal(t, "{\"key\":\"key\",\"value\":\"value\"}", w.Body.String())
	})

	t.Run("Should return 404 when key does not exist in store", func(t *testing.T) {
		mockStore.On("Get", "keydoesnotexist").Return(Message{}, badger.ErrKeyNotFound)
		w := httptest.NewRecorder()
		req, _ := http.NewRequest("GET", "/keydoesnotexist", nil)

		router.ServeHTTP(w, req)

		assert.Equal(t, 404, w.Code)
		assert.Empty(t, w.Body.String())
	})

	t.Run("Should return 500 when key does not exist in store", func(t *testing.T) {
		mockStore.On("Get", "error").Return(Message{}, badger.ErrInvalidRequest)
		w := httptest.NewRecorder()
		req, _ := http.NewRequest("GET", "/error", nil)

		router.ServeHTTP(w, req)

		assert.Equal(t, 500, w.Code)
		assert.Empty(t, w.Body.String())
	})

	t.Run("Should return value from Topology when key does not exist in current store but exists in cluster", func(t *testing.T) {
		var members []clustering.Member
		mockMember := mockMember{}
		members = append(members, &mockMember)
		ep.Members = members
		mockStore.On("Get", "clusterkey").Return(Message{}, badger.ErrKeyNotFound)
		mockMember.On("Get", "clusterkey").Return("valuecluster")
		w := httptest.NewRecorder()
		req, _ := http.NewRequest("GET", "/clusterkey", nil)

		router.ServeHTTP(w, req)

		assert.Equal(t, 200, w.Code)
		assert.Equal(t, "{\"key\":\"clusterkey\",\"value\":\"valuecluster\"}", w.Body.String())
	})

	ep.Members = nil
}

func TestEndPoint_Put(t *testing.T) {
	mockStore := mockStore{}

	ep := EndPoint{
		Store: &mockStore,
		Topology: &clustering.Topology{
			Current: "test",
			Members: nil,
		},
	}
	router := gin.Default()
	router.PUT("/:key", ep.Put)

	t.Run("Should return 201 when messages has been Put on store", func(t *testing.T) {
		mockStore.On("Put", Message{"key", "value"}).Return(nil)
		w := httptest.NewRecorder()
		req, _ := http.NewRequest("PUT", "/key", bytes.NewReader([]byte("{\"key\":\"key\",\"value\":\"value\"}")))

		router.ServeHTTP(w, req)

		assert.Equal(t, 201, w.Code)
	})

	t.Run("Should return 400 when messages is invalid", func(t *testing.T) {
		w := httptest.NewRecorder()
		req, _ := http.NewRequest("PUT", "/key", bytes.NewReader([]byte("{\"key\":\"\",\"value\":\"\"}")))

		router.ServeHTTP(w, req)

		assert.Equal(t, 400, w.Code)
	})

	t.Run("Should return 500 when messages is invalid", func(t *testing.T) {
		mockStore.On("Put", Message{"keyinerror", "valueinerror"}).Return(badger.ErrBlockedWrites)
		w := httptest.NewRecorder()
		req, _ := http.NewRequest("PUT", "/keyinerror", bytes.NewReader([]byte("{\"key\":\"keyinerror\",\"value\":\"valueinerror\"}")))

		router.ServeHTTP(w, req)

		assert.Equal(t, 500, w.Code)
	})
}
