package clustering

const REQUESTED_BY_HEADER_NAME = "X-REQUESTED-BY-MEMBER"

type Topology struct {
	Current string
	Members []Member
}

func (t *Topology) Get(key string) string {
	for _, member := range t.Members {
		value := member.Get(key)
		if value != "" {
			return value
		}
	}
	return ""
}
