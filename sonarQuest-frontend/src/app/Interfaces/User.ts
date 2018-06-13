import {Role} from './Role';
import {Level} from './Level';
import {Artefact} from './Artefact';
import {AvatarClass} from './AvatarClass';
import {AvatarRace} from './AvatarRace';

export interface User {
  id?: number,
  username: string,
  role: Role,
  picture?: string,
  aboutMe?: string,
  avatarClass?: AvatarClass,
  avatarRace?: AvatarRace,
  gold?: number,
  xp?: number,
  level?: Level,
  artefacts?: Artefact[],
  password?: string
}



