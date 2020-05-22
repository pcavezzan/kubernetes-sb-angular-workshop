import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpErrorResponse,
  HttpHandler,
  HttpEvent,
  HttpResponse
} from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

const HTTP_STATUS_UNKNOWN = -1;

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMsg: string;
        let statusCode: number;
        if (error.error instanceof Error) {
          // A client-side or network error occurred. Handle it accordingly.
          errorMsg = `${request.url} - an error occurred: ${error.error.message}`;
          statusCode = HTTP_STATUS_UNKNOWN;
          console.error('Client side error while trying to reach ' + request.url + ': ' + error.error.message);
        } else {
          // The backend returned an unsuccessful response code.
          // The response body may contain clues as to what went wrong,
          errorMsg = `${request.url} - returned code ${error.status}`;
          statusCode = error.status;
          console.error(request.url + ' return an error status (' + error.status + '): ' + error.message);
        }

        // If you want to return a new response:
        return of(new HttpResponse({
          body: {
            payload: errorMsg,
            error: {
              requestedUrl: request.url,
              status: statusCode
            }
          }
        }));

        // If you want to return the error on the upper level:
        // return throwError(error);

        // or just return nothing:
        // return EMPTY;
      })
    );
  }
}
