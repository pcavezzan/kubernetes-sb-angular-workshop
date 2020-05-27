package persistence

import "github.com/dgraph-io/badger/v2"

type Store struct {
	Db *badger.DB
}

func (s *Store) Get(key string) (string, error) {
	var value string
	err := s.Db.View(func(txn *badger.Txn) error {
		item, err := txn.Get([]byte(key))
		if err == nil {
			err = item.Value(func(val []byte) error {
				valCopy := append([]byte{}, val...)
				value = string(valCopy)
				return nil
			})
		}
		return err
	})
	return value, err
}

func (s *Store) Put(key, value string) error {
	return s.Db.Update(func(txn *badger.Txn) error {
		err := txn.Set([]byte(key), []byte(value))
		return err
	})
}
