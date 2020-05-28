package messages

import (
	"github.com/dgraph-io/badger/v2"
	"github.com/stretchr/testify/assert"
	"log"
	"os"
	"testing"
)

func TestStore_get(t *testing.T) {

	// Setup
	badgerDataDirTest := "/tmp/badger-store-test"
	t.Run("should Get value from badger", func(t *testing.T) {
		db, err := badger.Open(badger.DefaultOptions(badgerDataDirTest))
		if err != nil {
			log.Fatal(err)
		}
		defer db.Close()
		db.Update(func(txn *badger.Txn) error {
			return txn.Set([]byte("key"), []byte("value"))
		})
		store := NewBadgerStore(db)

		value, _ := store.Get("key")

		assert.Equal(t, value, Message{"key", "value"})
	})

	t.Run("should return an error from badger", func(t *testing.T) {
		db, err := badger.Open(badger.DefaultOptions(badgerDataDirTest))
		if err != nil {
			log.Fatal(err)
		}
		defer db.Close()
		store := NewBadgerStore(db)
		db.Close()

		_, err = store.Get("key")

		assert.Error(t, err)
	})

	// TearDown
	os.RemoveAll(badgerDataDirTest)

}

func TestStore_put(t *testing.T) {
	// Setup
	badgerDataDirTest := "/tmp/badger-store-test"

	t.Run("should Put message into badger", func(t *testing.T) {
		db, err := badger.Open(badger.DefaultOptions(badgerDataDirTest))
		if err != nil {
			log.Fatal(err)
		}
		defer db.Close()
		store := NewBadgerStore(db)

		store.Put(Message{"key", "value"})

		db.View(func(txn *badger.Txn) error {
			item, err := txn.Get([]byte("key"))
			assert.NoError(t, err)
			if err == nil {
				item.Value(func(val []byte) error {
					assert.Equal(t, val, []byte("value"))
					return nil
				})
			}
			return err
		})
	})

	t.Run("should return an error", func(t *testing.T) {
		db, err := badger.Open(badger.DefaultOptions(badgerDataDirTest))
		if err != nil {
			log.Fatal(err)
		}
		defer db.Close()
		store := NewBadgerStore(db)
		db.Close()

		err = store.Put(Message{"key", "value"})

		assert.Error(t, err)
	})

	// TearDown
	os.RemoveAll(badgerDataDirTest)

}
