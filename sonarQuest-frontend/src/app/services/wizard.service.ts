import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Wizard } from '../Interfaces/Wizard';
import { Observable } from 'rxjs';
import { WorldService } from './world.service';
import { World } from '../Interfaces/World';

@Injectable()
export class WizardService {

  private world: World;

  constructor(private http: HttpClient, private worldService: WorldService) {

    worldService.currentWorld$.subscribe(world => this.world = world)
  }

  public getWizardMessage(): Observable<Wizard> {
      return this.http.get<Wizard>(`${environment.endpoint}/wizard/world/${this.world ? this.world.id : ''}`);
  }

}
