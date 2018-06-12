import {Participation} from './Participation';
import {Quest} from './Quest';

export interface StandardTask {
  id: number,
  title: string,
  status: string,
  gold: number,
  xp: number,
  quest: Quest,
  world: null,
  taskType: string,
  participation: Participation,
  key: string,
  component: string,
  severity: string,
  type: string,
  debt: number,
  issueKey: string

}
