import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MessageComponent } from './message/message.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { REST_API_URL_TOKEN } from './app.di.token';
import { environment } from '../environments/environment';
import { NgTerminalModule } from 'ng-terminal';
import { InfoComponent } from './info/info.component';
import { HttpErrorInterceptor } from './shared/http-error.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    MessageComponent,
    InfoComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    NgTerminalModule
  ],
  providers: [
    { provide: REST_API_URL_TOKEN, useValue: environment.apiUrl },
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
