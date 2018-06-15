import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {LocalStorageService} from './local-storage.service';
import {Token} from './token';
import {Router} from '@angular/router';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

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

    return next.handle(request).do((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          // do stuff with response if you want
        }
      }, (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status === 401) {
            console.log('Session timeout');
            this.router.navigate(['/'], {skipLocationChange: true});
          }
        }
      }
    );

  }
}
