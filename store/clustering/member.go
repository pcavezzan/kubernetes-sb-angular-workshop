package clustering

import (
	"encoding/json"
	"fmt"
	"github.com/go-resty/resty/v2"
	"log"
)

type Member interface {
	Id() string
	Get(key string) string
}

type message struct {
	Key   string `json:"key"`
	Value string `json:"value"`
}

type memberImpl struct {
	id     string
	client *resty.Client
}

func NewMemberImpl(id string, client *resty.Client) *memberImpl {
	return &memberImpl{id: id, client: client}
}

func (m *memberImpl) Id() string {
	return m.id
}

func (m *memberImpl) Get(key string) string {
	getPath := fmt.Sprintf("/messages/%s", key)
	log.Printf("Getting messages from other member(%s): %s", m.id, getPath)
	if response, err := m.client.R().SetHeader(REQUESTED_BY_HEADER_NAME, m.id).Get(getPath); err == nil {
		msg := message{}
		log.Printf("memberImpl responded %s with the following body: %s", response.Status(), string(response.Body()))
		if err := json.Unmarshal(response.Body(), &msg); err == nil {
			log.Printf("Unmarshalling into JSON succeeded wit the following msg: %+v", msg)
			return msg.Value
		}
	} else {
		log.Println(err)
	}

	return ""
}
