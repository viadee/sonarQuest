import {Skill} from './Skill';
import {Level} from './Level';

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
