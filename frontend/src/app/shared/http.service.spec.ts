import { HttpService } from './http.service';
import { HttpClient } from '@angular/common/http';

describe('HttpService', () => {
  let httpClientSpy: jasmine.SpyObj<HttpClient>;
  let httpService: HttpService;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    httpService = new HttpService(httpClientSpy, 'https://backend:8443/api');
  });

  it('get should delegate rest call to httpClient', () => {
    httpService.get('/');

    expect(httpClientSpy.get).toHaveBeenCalled();
  });

  it('get should pass the right rest backend api url to httpClient', () => {
    httpService.get('/foo');

    expect(httpClientSpy.get).toHaveBeenCalledWith('https://backend:8443/api/foo');
  });

});
