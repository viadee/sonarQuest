import {ReplaySubject, Subject, Observable} from 'rxjs';
import {DomSanitizer} from '@angular/platform-browser';
import {Injectable} from '@angular/core';

@Injectable()
export class ImageService {

  imageSubject: Subject<any> = new ReplaySubject(1);

  constructor(private domSanitizer: DomSanitizer) {
  }

  createImageFromBlob(image: Blob): Observable<any> {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imageSubject.next(this.domSanitizer.bypassSecurityTrustUrl(reader.result.toString()));
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
    return this.imageSubject;
  }


  /**
   * Do not change with createImageFromBlob(image: Blob)
   * With one Subject in this class, every image will become the same 
   */
  createImageFromBlob2(image: Blob): Observable<any> {

    var imageSubject: Subject<any> = new Subject;
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      imageSubject.next(this.domSanitizer.bypassSecurityTrustUrl(reader.result.toString()));
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
    return imageSubject;
  }

}
