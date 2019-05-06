import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Permission} from '../Interfaces/Permission';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {AuthenticationService} from '../authentication/authentication.service';

@Injectable()
export class PermissionService {

  private permittedUrls: string[];

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    authenticationService.onLoginLogout().subscribe(() => {
      if (authenticationService.isLoggedIn()) {
        this.loadPermissions();
      }
    });
  }

  public loadPermissions(): Promise<any> {
    return this.loadPermittedUrls().then(pUrls => this.permittedUrls = pUrls);
  }

  private getPermissions(): Observable<Permission[]> {
    return this.http.get<Permission[]>(`${environment.endpoint}/permission`);
  }

  private loadPermittedUrls(): Promise<string[]> {
    return this.getPermissions().pipe(map(permissions => permissions.map(permission => permission.permission))).toPromise();
  }

  public isUrlPermitted(url: string): boolean | Promise<boolean> {
    if (this.permittedUrls) {
      return this.permittedUrls.includes(url);
    } else {
      return this.loadPermissions().then(() => this.permittedUrls.includes(url));
    }
  }

  public isUrlVisible(url: string): boolean {
    return this.permittedUrls.includes(url);
  }

}
