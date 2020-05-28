package messages

import (
	"errors"
	"github.com/dgraph-io/badger/v2"
	"github.com/gin-gonic/gin"
	"github.com/pcavezzan/kubernetes/workshop/store/clustering"
)

type EndPoint struct {
	Store
	*clustering.Topology
}

func (e *EndPoint) Get(c *gin.Context) {
	key := c.Param("key")
	if message, err := e.Store.Get(key); err == nil {
		c.JSON(200, message)
	} else if errors.Is(badger.ErrKeyNotFound, err) {
		if e.requestNeedsToBeDispatchedToTopology(c) {
			if value := e.Topology.Get(key); value != "" {
				message.Key = key
				message.Value = value
				c.JSON(200, message)
				return
			}
		}
		c.AbortWithStatus(404)
	} else {
		c.AbortWithError(500, err)
	}
}

func (e *EndPoint) Put(c *gin.Context) {
	msg := Message{
		Value: c.PostForm("value"),
	}

	if msg.IsInvalid() {
		c.BindJSON(&msg)
	}

	msg.Key = c.Param("key")

	if msg.IsInvalid() {
		c.AbortWithStatus(400)
	} else if err := e.Store.Put(msg); err == nil {
		c.JSON(201, gin.H{})
	} else {
		c.AbortWithStatus(500)
	}
}

func (e *EndPoint) requestNeedsToBeDispatchedToTopology(c *gin.Context) bool {
	return c.GetHeader(clustering.REQUESTED_BY_HEADER_NAME) == "" && e.Topology != nil
}
