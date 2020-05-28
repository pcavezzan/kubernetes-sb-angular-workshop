package health

import (
	"github.com/gin-gonic/gin"
)

type EndPoint struct {
}

func (e *EndPoint) Ping(c *gin.Context) {
	c.Status(204)
}
