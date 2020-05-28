package messages

type Message struct {
	Key   string `json:"key"`
	Value string `json:"value"`
}

func (msg Message) IsInvalid() bool {
	return msg.hasNoKey() || msg.hasNoValue()
}

func (msg Message) hasNoKey() bool {
	return msg.Key == ""
}

func (msg Message) hasNoValue() bool {
	return msg.Value == ""
}
