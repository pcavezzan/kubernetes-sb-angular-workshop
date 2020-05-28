package clustering

import (
	"github.com/go-resty/resty/v2"
	"github.com/stretchr/testify/assert"
	"gopkg.in/h2non/gock.v1"
	"net/http"
	"testing"
)

func TestMemberImpl_Id(t *testing.T) {
	member := memberImpl{
		id:     "store-test",
		client: nil,
	}

	result := member.Id()

	assert.Equal(t, result, "store-test")
}

func TestMemberImpl_Get(t *testing.T) {
	defer gock.Off() // Flush pending mocks after test execution
	httpClient := http.Client{Transport: &http.Transport{}}
	rClient := resty.NewWithClient(&httpClient).SetHostURL("http://member-2")
	gock.InterceptClient(&httpClient)
	defer gock.RestoreClient(&httpClient)

	member := memberImpl{
		id:     "store-test",
		client: rClient,
	}

	t.Run("Should return message from remote server", func(t *testing.T) {
		gock.New("http://member-2").HeaderPresent(REQUESTED_BY_HEADER_NAME).Get("/messages/key").
			Reply(200).
			JSON(map[string]string{"key": "key", "value": "value"})

		value := member.Get("key")

		assert.Equal(t, value, "value")
	})

	t.Run("Should return empty message when remote server do not respond OK", func(t *testing.T) {
		gock.New("http://member-2").HeaderPresent(REQUESTED_BY_HEADER_NAME).Get("/messages/key").
			Reply(404)

		value := member.Get("key")

		assert.Equal(t, value, "")
	})

	t.Run("Should return empty message when remote server do not return JSON response", func(t *testing.T) {
		gock.New("http://member-2").HeaderPresent(REQUESTED_BY_HEADER_NAME).Get("/messages/key").
			Reply(200).
			BodyString("'value': 'message'")

		value := member.Get("key")

		assert.Equal(t, value, "")
	})

}
