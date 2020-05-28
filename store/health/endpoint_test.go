package health

import (
	"github.com/gin-gonic/gin"
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestEndPoint_Ping(t *testing.T) {
	ep := EndPoint{}
	router := gin.Default()
	router.GET("/ping", ep.Ping)

	t.Run("Should return 204", func(t *testing.T) {
		assert.HTTPSuccess(t, router.ServeHTTP, "GET", "/ping", nil)
	})
}
