package gin

import (
	"github.com/gin-gonic/gin"
	"github.com/pcavezzan/kubernetes/workshop/store/clustering"
	"github.com/pcavezzan/kubernetes/workshop/store/health"
	"github.com/pcavezzan/kubernetes/workshop/store/messages"
)

func NewMainRouter(store messages.Store, topology *clustering.Topology) *gin.Engine {
	messageEndPoint := messages.EndPoint{Store: store, Topology: topology}
	pingEndPoint := health.EndPoint{}

	r := gin.Default()
	r.GET("/messages/:key", messageEndPoint.Get)
	r.PUT("/messages/:key", messageEndPoint.Put)
	r.GET("/ping", pingEndPoint.Ping)

	return r
}
