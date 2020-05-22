import { Observable, zip } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpService } from '../shared/http.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private httpService: HttpService) {
  }

  getFrontPageMessage(): Observable<Message> {
    return zip(
      this.httpService.get<Message>('/welcome'),
      this.httpService.get<Message>('/whoami')
    ).pipe(
      map(values => {
        let msg: string;
        const welcomeMsg = values[0];
        const whoamiMsg = values[1];

        if (welcomeMsg.error && whoamiMsg.error) {
          msg = `backend server error status: ${welcomeMsg.error.status}`;
        } else {
          msg = welcomeMsg.payload + ' from ' + whoamiMsg.payload;
        }

        return {
          payload: msg
        };
      })
    );
  }

  getInfoMessage(): Observable<Message> {
    return this.httpService.get<Message>('/info');
  }

}
