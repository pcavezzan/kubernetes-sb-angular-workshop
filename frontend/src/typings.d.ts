interface Message {
  payload: string;
  error?: HttpClientError;
}

interface HttpClientError {
  requestedUrl: string;
  status: number;
}
