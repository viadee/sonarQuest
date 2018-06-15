import {Task} from './Task';
import {User} from './User';
import {Quest} from './Quest';

export interface Participation {
  id: number,
  tasks?: Task[],
  quest?: Quest,
  user: User;
}
