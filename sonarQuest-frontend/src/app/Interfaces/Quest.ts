import {World} from './World';
import {Task} from './Task';
import {Participation} from './Participation';
import {Adventure} from './Adventure';

export interface Quest {
  id: number,
  title: string,
  story: string,
  status: string,
  gold: number,
  xp: number,
  image: string,
  world: World,
  adventure: Adventure,
  tasks: Task[];
  participations: Participation[]
}


