import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { MessageService } from './message.service';


@Component({
  selector: 'app-message',
  template: `
    <span class="value">{{ (value$ | async)?.payload }}</span>
  `
})
export class MessageComponent implements OnInit {

  value$: Observable<Message>;

  constructor(private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.value$ = this.messageService.getMessage();
  }

}
