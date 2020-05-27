package web

import (
	"github.com/gin-gonic/gin"
)

type HealthEndPoint struct {
}

func (e *HealthEndPoint) Ping(c *gin.Context)  {
	c.Status(204)
}
