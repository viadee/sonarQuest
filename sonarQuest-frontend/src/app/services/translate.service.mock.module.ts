import { Injectable, NgModule, Pipe, PipeTransform } from '@angular/core';
import { TranslateLoader, TranslateModule, TranslatePipe, TranslateService } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';


const translations: any = {};

<<<<<<< HEAD
class FakeLoader implements TranslateLoader {
=======
export class FakeLoader implements TranslateLoader {
>>>>>>> refs/remotes/origin/issue-124-AddLoginPage
  getTranslation(lang: string): Observable<any> {
    return of(translations);
  }
}

@Pipe({
  name: 'translate'
})
export class TranslatePipeMock implements PipeTransform {
  public name = 'translate';

  public transform(query: string, ...args: any[]): any {
    return query;
  }
}

@Injectable()
export class TranslateServiceStub {
  public get<T>(key: T): Observable<T> {
    return of(key);
  }

  public setDefaultLang(lang: string): void {}

  public getBrowserLang(): string {
    return "de";
  }

  public use(lang: string): Observable<any> {
    return of("de");
  }
}

@NgModule({
  declarations: [
    TranslatePipeMock
  ],
  providers: [
    { provide: TranslateService, useClass: TranslateServiceStub },
    { provide: TranslatePipe, useClass: TranslatePipeMock },
  ],
  imports: [
    TranslateModule.forRoot({
      loader: { provide: TranslateLoader, useClass: FakeLoader },
    })
  ],
  exports: [
    TranslatePipeMock,
    TranslateModule
  ]
})
export class TranslateTestingModule {

}
