import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';
import {DomSanitizer} from '@angular/platform-browser';
import {Injectable} from '@angular/core';
import { environment } from 'environments/environment.prod';

@Injectable()
export class ImageService {

  imageSubject: Subject<any> = new ReplaySubject(1);

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

  createAvatarImageUrl(userId: string) {
    return `${environment.endpoint}/${userId}/avatar`;
  }

}
