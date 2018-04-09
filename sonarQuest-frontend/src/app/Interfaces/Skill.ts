export interface AvatarClass {
    id: number,
    name: string
}


export interface Skill {
    id: number,
    name: string,
    type: string,
    value: number,
    avatarClasses: AvatarClass[]
}