import {World} from './World';
import {Task} from './Task';
import {Participation} from './Participation';
import {Adventure} from './Adventure';
import { QuestState } from './QuestState';

export interface Quest {
  id: number,
  title: string,
  story: string,
  status: QuestState,
  gold: number,
  xp: number,
  image: string,
  visible: boolean,
  startdate: Date,
  enddate: Date,
  world: World,
  adventure: Adventure,
  tasks: Task[];
  participations: Participation[];
  participants: string[];
}


