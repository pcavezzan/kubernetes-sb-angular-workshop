package messages

import "testing"

func TestMessage_IsInvalid(t *testing.T) {
	type fields struct {
		Key   string
		Value string
	}

	tests := []struct {
		name   string
		fields fields
		want   bool
	}{
		{
			name:   "Should return true when key is empty",
			fields: fields{Key: "", Value: "Hello"},
			want:   true,
		},
		{
			name:   "Should return true when value is empty",
			fields: fields{Key: "Hello", Value: ""},
			want:   true,
		},
		{
			name:   "Should return true when both key and value are empty",
			fields: fields{Key: "", Value: ""},
			want:   true,
		},
		{
			name:   "Should return false when both key and value are not empty",
			fields: fields{Key: "Hello", Value: "World"},
			want:   false,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			msg := Message{
				Key:   tt.fields.Key,
				Value: tt.fields.Value,
			}
			if got := msg.IsInvalid(); got != tt.want {
				t.Errorf("IsInvalid() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestMessage_hasNoKey(t *testing.T) {
	type fields struct {
		Key   string
		Value string
	}
	tests := []struct {
		name   string
		fields fields
		want   bool
	}{
		{
			name:   "should return true when key is empty",
			fields: fields{Key: "", Value: "World"},
			want:   true,
		},
		{
			name:   "should return false when key is not empty",
			fields: fields{Key: "Hello", Value: ""},
			want:   false,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			msg := Message{
				Key:   tt.fields.Key,
				Value: tt.fields.Value,
			}
			if got := msg.hasNoKey(); got != tt.want {
				t.Errorf("hasNoKey() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestMessage_hasNoValue(t *testing.T) {
	type fields struct {
		Key   string
		Value string
	}
	tests := []struct {
		name   string
		fields fields
		want   bool
	}{
		{
			name:   "should return true when value is empty",
			fields: fields{Key: "Hello", Value: ""},
			want:   true,
		},
		{
			name:   "should return false when value is not empty",
			fields: fields{Key: "Hello", Value: "World"},
			want:   false,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			msg := Message{
				Key:   tt.fields.Key,
				Value: tt.fields.Value,
			}
			if got := msg.hasNoValue(); got != tt.want {
				t.Errorf("hasNoValue() = %v, want %v", got, tt.want)
			}
		})
	}
}
