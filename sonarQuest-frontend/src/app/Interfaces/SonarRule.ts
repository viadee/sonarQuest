import { UserSkill } from './UserSkill';

export interface SonarRule {
  id?: number,
  name: string,
  key: string,
  addedAt: Date,
  userSkill?: UserSkill,
}
