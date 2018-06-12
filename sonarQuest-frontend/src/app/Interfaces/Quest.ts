import {World} from './World';
import {Task} from './Task';
import {Participation} from './Participation';

export interface Quest {
  id: number,
  title: string,
  story: string,
  status: string,
  gold: number,
  xp: number,
  image: string,
  world: World,
  adventure: {
    id: number,
    title: string,
    story: string,
    status: string,
    gold: string,
    xp: string
  },
  tasks: Task[];
  participations: Participation[]
}


