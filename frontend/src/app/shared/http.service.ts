import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { REST_API_URL_TOKEN } from '../app.di.token';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  constructor(private httpClient: HttpClient, @Inject(REST_API_URL_TOKEN) private backendRestUrl: string) {
  }

  get<T>(url: string): Observable<T> {
    return this.httpClient.get<T>(`${this.backendRestUrl}${url}`);
  }
}
