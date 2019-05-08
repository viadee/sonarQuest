import { SonarRule } from './SonarRule';
import { UserSkillGroup } from './UserSkillGroup';

export interface UserSkill {
    id?: number,
    name: string,
    description?: string,
    isRoot?: boolean,
    followingUserSkills: UserSkill[],
    previousUserSkills?: UserSkill[]
    sonarRules?: SonarRule[],
    userSkillGroup?: UserSkillGroup,
    root?: boolean;
    requiredRepetitions: number
}
