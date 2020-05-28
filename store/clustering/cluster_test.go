package clustering

import (
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
	"testing"
)

// ========= Mocking Topology =============
type mockMember struct {
	mock.Mock
}

func (m *mockMember) Id() string {
	args := m.Called()
	return args.String()
}
func (m *mockMember) Get(key string) string {
	args := m.Called(key)
	return args.String(0)
}

func TestTopology_Get(t *testing.T) {

	members := []Member{}
	firstMember := mockMember{}
	members = append(members, &firstMember)
	secondMember := mockMember{}
	members = append(members, &secondMember)

	topology := Topology{
		Current: "store-test",
		Members: members,
	}

	t.Run("Should return firstMember value when firstMember has the value", func(t *testing.T) {
		firstMember.On("Get", "keyFirst").Return("firstValue")

		value := topology.Get("keyFirst")

		assert.Equal(t, value, "firstValue")
		secondMember.AssertNotCalled(t, "Get", "keyFirst")
	})

	t.Run("Should return secondMember value when firstMember does not have the value", func(t *testing.T) {
		firstMember.On("Get", "keySecond").Return("")
		secondMember.On("Get", "keySecond").Return("secondValue")

		value := topology.Get("keySecond")

		assert.Equal(t, value, "secondValue")
		firstMember.AssertCalled(t, "Get", "keySecond")
	})

	t.Run("Should return empty value when no members have the value", func(t *testing.T) {
		firstMember.On("Get", "keyThird").Return("")
		secondMember.On("Get", "keyThird").Return("")

		value := topology.Get("keyThird")

		assert.Empty(t, value)
		firstMember.AssertCalled(t, "Get", "keyThird")
		firstMember.AssertCalled(t, "Get", "keyThird")
	})

}
