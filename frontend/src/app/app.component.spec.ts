import { async, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MessageComponent } from './message/message.component';
import { REST_API_URL_TOKEN } from './app.di.token';
import { MessageService } from './message/message.service';
import { HttpService } from './shared/http.service';
import { InfoComponent } from './info/info.component';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers: [
        {provide: REST_API_URL_TOKEN, useValue: '/api'},
        {provide: MessageService, useValue: jasmine.createSpy('MessageService')},
        {provide: HttpService, useValue: jasmine.createSpy('HttpService')}
      ],
      imports: [
        HttpClientTestingModule
      ],
      declarations: [
        AppComponent,
        MessageComponent,
        InfoComponent
      ],
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'frontend'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('frontend');
  });
});
