import {Level} from './Level';
import {Artefact} from './Artefact';
import {AvatarClass} from './AvatarClass';
import {AvatarRace} from './AvatarRace';
import {World} from './World';
import { Role } from './Role';

export interface User {
  id?: number,
  username: string,
  mail?: string,
  role: Role,
  picture?: string,
  aboutMe?: string,
  avatarClass?: AvatarClass,
  avatarRace?: AvatarRace,
  gold?: number,
  xp?: number,
  level?: Level,
  artefacts?: Artefact[],
  password?: string,
  currentWorld?: World,
  joinedWorlds?: string[],
  lastLogin?: string
}



