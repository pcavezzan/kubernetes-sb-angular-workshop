import { TestBed, async } from '@angular/core/testing';
import { MessageComponent } from './message.component';
import { MessageService } from './message.service';
import { cold } from 'jasmine-marbles';

describe('MessageComponent', () => {
  let messageServiceSpy: jasmine.SpyObj<MessageService>;
  beforeEach(async(() => {
    messageServiceSpy = jasmine.createSpyObj('MessageService', ['getFrontPageMessage', 'getInfoMessage']);

    TestBed.configureTestingModule({
      providers: [
        {provide: MessageService, useValue: messageServiceSpy}
      ],
      declarations: [
        MessageComponent,
      ],
    }).compileComponents();

  }));

  it('should create the app-message', () => {
    const fixture = TestBed.createComponent(MessageComponent);
    const messageComponent = fixture.componentInstance;

    expect(messageComponent).toBeTruthy();
  });

  it(`should have called message service`, () => {
    const fixture = TestBed.createComponent(MessageComponent);
    const component = fixture.componentInstance;

    component.ngOnInit();

    expect(messageServiceSpy.getFrontPageMessage).toHaveBeenCalled();
  });

  it('should have value observable', () => {
    messageServiceSpy.getFrontPageMessage.and.callFake(() => cold('-m-', {m: 'Hello World'}));
    const fixture = TestBed.createComponent(MessageComponent);
    const component = fixture.componentInstance;

    component.ngOnInit();

    expect(component.message$).toBeObservable(cold('-m-', {m: 'Hello World'}));
  });

});
