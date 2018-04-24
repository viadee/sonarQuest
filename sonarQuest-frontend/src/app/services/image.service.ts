import { ReplaySubject } from 'rxjs/ReplaySubject';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { DomSanitizer } from '@angular/platform-browser';
import { Injectable } from '@angular/core';

@Injectable()
export class ImageService {

  imageSubject: Subject<any> = new ReplaySubject(1);
  image$ = this.imageSubject.asObservable();

  constructor(
    private domSanitizer: DomSanitizer
  ) { }


  createImageFromBlob(image: Blob): Observable<any> {

    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageSubject.next(this.domSanitizer.bypassSecurityTrustUrl(reader.result));
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
    return this.imageSubject;
  }

}
