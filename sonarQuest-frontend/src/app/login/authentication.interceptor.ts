import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { LocalStorageService } from './local-storage.service';
import { Token } from './Token';
import { Router } from '@angular/router';




@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private storageService: LocalStorageService, private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: Token = this.storageService.getJWTToken();

    if (token) {
      request = request.clone({
        headers: request.headers.set('Authorization', 'Bearer ' + token.getJwt())
      });
    }

    return next.handle(request).pipe(tap(
      event => this.handleResponse(request, event),
      error => this.handleError(request, error)
      )
    );
  }

  handleResponse(req: HttpRequest<any>, event) {
    console.log('Handling response for ', req.url, event);
    if (event instanceof HttpResponse) {
      console.log('Request for ', req.url,
        ' Response Status ', event.status,
        ' With body ', event.body);
    }
  }

  handleError(req: HttpRequest<any>, event) {
    if (event.status === 401) {
      console.log('Session timeout');
      this.router.navigate(['/'], {skipLocationChange: true});
    }
  }
}


