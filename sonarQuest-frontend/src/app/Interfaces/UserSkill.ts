import { SonarRule } from './SonarRule';

export interface UserSkill {
  id: number,
  name: string,
  description: string,
  isRoot: boolean,
  followingUserSkills: UserSkill[],
  sonarRules: SonarRule[]
}
