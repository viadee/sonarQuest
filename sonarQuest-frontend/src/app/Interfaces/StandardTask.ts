import {Task} from './Task';

export interface StandardTask extends Task {
  component: string,
  severity: string,
  type: string,
  debt: number,
  issueKey: string

}
