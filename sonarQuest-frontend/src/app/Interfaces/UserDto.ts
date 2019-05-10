import {Role} from './Role';
import {Level} from './Level';
import {AvatarClass} from './AvatarClass';
import {AvatarRace} from './AvatarRace';

export interface UserDto {
  id: number,
  username: string,
  mail?: string,
  role: Role,
  picture?: any,
  aboutMe?: string,
  avatarClass?: AvatarClass,
  avatarRace?: AvatarRace,
  gold?: number,
  xp?: number,
  level?: Level,
  currentWorld?: number,
  lastLogin?: string
}
