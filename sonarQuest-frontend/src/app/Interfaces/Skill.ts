import {AvatarClass} from './AvatarClass';

export interface Skill {
    id: number,
    name: string,
    type: string,
    value: number,
    avatarClasses: AvatarClass[]
}
