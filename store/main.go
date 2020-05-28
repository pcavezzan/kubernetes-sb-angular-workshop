package main

import (
	"fmt"
	"github.com/dgraph-io/badger/v2"
	"github.com/go-resty/resty/v2"
	"github.com/pcavezzan/kubernetes/workshop/store/clustering"
	"github.com/pcavezzan/kubernetes/workshop/store/gin"
	"github.com/pcavezzan/kubernetes/workshop/store/messages"
	"github.com/spf13/pflag"
	"log"
	"os"
	"strings"
)

func main() {
	enableClustering := pflag.Bool("enableClustering", false, "Enable cluster behavior")
	clusterMembers := pflag.String("clusterMembers", "", "Identifier of all members in cluster (eg: member-1:9090,member-2:8080)")
	currentId := pflag.String("id", "", "Identifier of the actual member in cluster")

	pflag.Parse()

	log.Println("currentId: " + *currentId)
	log.Println("clusterMembers: " + *clusterMembers)

	var topology *clustering.Topology
	if *enableClustering {
		if *clusterMembers != "" {
			memberIds := strings.Split(*clusterMembers, ",")
			var members []clustering.Member
			for _, memberId := range memberIds {
				if memberId != *currentId {
					url := "http://" + memberId
					log.Printf("memberImpl about to be created with: %s", url)

					client := resty.New().SetHostURL(url)
					members = append(members, clustering.NewMemberImpl(memberId, client))
				}
			}
			topology = &clustering.Topology{Current: *currentId, Members: members}
		}
	}

	dataDir := os.Getenv("STORE_DATA_PATH")
	if dataDir == "" {
		dataDir = "/tmp/badger"
	}
	db, err := badger.Open(badger.DefaultOptions(dataDir))
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	store := messages.NewBadgerStore(db)

	r := gin.NewMainRouter(store, topology)

	serverPort := os.Getenv("STORE_SERVER_PORT")
	if serverPort == "" {
		serverPort = "9090"
	}
	r.Run(fmt.Sprintf(":%s", serverPort)) // listen and serve on 0.0.0.0:8080
}
