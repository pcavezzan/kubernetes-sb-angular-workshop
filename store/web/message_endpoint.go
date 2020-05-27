package web

import (
	"errors"
	"fmt"
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
	var message Message
	if err := c.BindJSON(&message); err != nil {
		fmt.Println("Bad put body")
		return
	}

	if err := e.Store.Put(key, message.Value); err == nil {
		c.JSON(201, gin.H{})
	}
}

type Message struct {
	Key   string `json:"key"`
	Value string `json:"value"`
}