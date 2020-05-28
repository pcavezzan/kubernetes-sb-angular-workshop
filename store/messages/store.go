package messages

import (
	"github.com/dgraph-io/badger/v2"
)

type Store interface {
	Get(key string) (Message, error)
	Put(message Message) error
}

func NewBadgerStore(Db *badger.DB) Store {
	return &badgerStore{Db}
}

type badgerStore struct {
	Db *badger.DB
}

func (s *badgerStore) Get(key string) (Message, error) {
	var message Message
	err := s.Db.View(func(txn *badger.Txn) error {
		item, err := txn.Get([]byte(key))
		if err == nil {
			err = item.Value(func(val []byte) error {
				message.Key = key
				valCopy := append([]byte{}, val...)
				message.Value = string(valCopy)
				return nil
			})
		}
		return err
	})
	return message, err
}

func (s *badgerStore) Put(msg Message) error {
	return s.Db.Update(func(txn *badger.Txn) error {
		err := txn.Set([]byte(msg.Key), []byte(msg.Value))
		return err
	})
}
