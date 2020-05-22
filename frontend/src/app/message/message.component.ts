import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {interval, Observable, Subject} from 'rxjs';
import {MessageService} from './message.service';
import {FunctionsUsingCSI, NgTerminal} from 'ng-terminal';
import {switchMap, takeUntil} from 'rxjs/operators';
import {environment} from '../../environments/environment';


@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements AfterViewInit, OnDestroy, OnInit {
  @ViewChild('term') child: NgTerminal;
  displayTerm: boolean;
  message$: Observable<Message>;
  info$: Observable<Message>;

  private destroy$: Subject<boolean> = new Subject<boolean>();
  private intervalInMillis: number;

  constructor(private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.intervalInMillis = environment.config.curl.timeInMillis;
    this.displayTerm = environment.config.curl.active;
    this.message$ = this.messageService.getFrontPageMessage();
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngAfterViewInit(): void {
    if (this.displayTerm) {
      this.child.underlying.setOption('fontSize', 18);
      this.child.write('$ ');
      interval(this.intervalInMillis)
        .pipe(
          takeUntil(this.destroy$),
          switchMap(() => this.messageService.getInfoMessage())
        )
        .subscribe(message => {
          this.child.write(message.payload);
          this.child.write('\n' + FunctionsUsingCSI.cursorColumn(1) + '$ '); //
        });
    } else {
      this.info$ = this.messageService.getInfoMessage();
    }

  }
}
