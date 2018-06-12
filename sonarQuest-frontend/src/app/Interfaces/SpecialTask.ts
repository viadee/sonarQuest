import {Quest} from './Quest';
import {Participation} from './Participation';

export interface SpecialTask {
  id: number,
  title: string,
  status: string,
  gold: number,
  xp: number,
  quest: Quest,
  world: null,
  taskType: string,
  participation: Participation,
  message: string
}
