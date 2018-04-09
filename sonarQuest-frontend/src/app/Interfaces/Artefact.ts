export interface Level {
    id: number,
    name: string ,
    min: number,
    max: number
  }

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

export interface Artefact {
    id: number,
    name: string,
    icon: string,
    price: number,
    quantity: number,
    description: string,
    minLevel: Level,
    skills: Skill[]
}