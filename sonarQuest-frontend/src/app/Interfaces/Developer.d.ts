

export interface Level {
  id: number,
  name: string ,
  min: number,
  max: number
}

export interface World{
  id: number,
  name: string,
  project: string,
  active: boolean
}


export interface AvatarClass{
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

export interface AvatarRace{
  id: number,
  name: string
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


export interface Developer {
  id: number,
  username: string ,
  gold: number,
  xp: number,
  level:  Level,
  picture: string,
  aboutMe: string,
  avatarClass: AvatarClass,
  avatarRace: AvatarRace,
  artefacts: Artefact[],
  world: World
}
