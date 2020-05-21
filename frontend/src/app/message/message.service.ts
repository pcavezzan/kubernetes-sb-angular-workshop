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

  getMessage(): Observable<Message> {
    return zip(
      this.httpService.get<Message>('/welcome'),
      this.httpService.get<Message>('/whoami')
    ).pipe(
      map(values => {
        const welcomeMsg = values[0].payload;
        const whoamiMsg = values[1].payload;
        return {
          payload: welcomeMsg + ' - ' + whoamiMsg
        };
      })
    );
  }

}
