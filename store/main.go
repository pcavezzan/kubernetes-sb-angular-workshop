package main

import (
	"fmt"
	"github.com/dgraph-io/badger/v2"
	"github.com/gin-gonic/gin"
	"github.com/pcavezzan/kubernetes/workshop/store/persistence"
	"github.com/pcavezzan/kubernetes/workshop/store/web"
	"log"
	"os"
)

func main() {
	dataDir := os.Getenv("STORE_DATA_PATH")
	if dataDir == "" {
		dataDir = "/tmp/badger"
	}
	db, err := badger.Open(badger.DefaultOptions(dataDir))
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	store := persistence.Store{Db: db}
	messageEndPoint := web.MessageEndPoint{Store: &store}
	pingEndPoint := web.HealthEndPoint{}

	r := gin.Default()
	r.GET("/messages/:key", messageEndPoint.Get)
	r.PUT("/messages/:key", messageEndPoint.Put)
	r.GET("/ping", pingEndPoint.Ping)

	serverPort := os.Getenv("STORE_SERVER_PORT")
	if serverPort == "" {
		serverPort = "9090"
	}

	r.Run(fmt.Sprintf(":%s", serverPort)) // listen and serve on 0.0.0.0:8080
}
