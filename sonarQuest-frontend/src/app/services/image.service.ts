import {Subject, Observable} from 'rxjs';
import {DomSanitizer} from '@angular/platform-browser';
import {Injectable} from '@angular/core';

@Injectable()
export class ImageService {

  constructor(private domSanitizer: DomSanitizer) {
  }

  createImageFromBlob(image: Blob): Observable<any> {

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
