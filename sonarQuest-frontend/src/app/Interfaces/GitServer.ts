import {World} from './World';

export interface GitServer {
  id: number,
  url: string,
  username?: string,
  password?: string,
  world: World
}
