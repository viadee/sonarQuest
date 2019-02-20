import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import {Injectable} from '@angular/core';

@Injectable()
export class ImageService {

  imageSubject: Subject<any> = new ReplaySubject(1);


  key: Blob[]
  value: Subject<any>

  constructor(private domSanitizer: DomSanitizer) {
  }

  createImageFromBlob(image: Blob): Observable<any> {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imageSubject.next(this.domSanitizer.bypassSecurityTrustUrl(reader.result));
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
    return this.imageSubject;
  }


  /**
   * Do not change with createImageFromBlob(image: Blob)
   * With a global Subject in this class, every image will become the same 
   */
  createImageFromBlob2(image: Blob): Observable<any> {

    var imageSubject: Subject<any> = new Subject;
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      imageSubject.next(this.domSanitizer.bypassSecurityTrustUrl(reader.result));
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
    return imageSubject;
  }

}
