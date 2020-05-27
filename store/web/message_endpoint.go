package web

import (
	"errors"
	"github.com/dgraph-io/badger/v2"
	"github.com/gin-gonic/gin"
	"github.com/pcavezzan/kubernetes/workshop/store/persistence"
)

type MessageEndPoint struct {
	Store *persistence.Store
}

func (e *MessageEndPoint) Get(c *gin.Context) {
	key := c.Param("key")
	if value, err := e.Store.Get(key); err == nil {
		c.JSON(200, gin.H{
			"key":   key,
			"value": value,
		})
	} else if errors.Is(badger.ErrKeyNotFound, err) {
		c.AbortWithStatus(404)
	} else {
		c.AbortWithError(500, err)
	}
}

func (e *MessageEndPoint) Put(c *gin.Context) {
	key := c.Param("key")
	value := c.PostForm("value")
	if err := e.Store.Put(key, value); err == nil {
		c.JSON(201, gin.H{})
	}
}
