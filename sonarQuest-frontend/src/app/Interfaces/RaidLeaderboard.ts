import { UserDto } from './UserDto';

export interface RaidLeaderboard {
    scoreGold: number,
    scoreXp: number,
    scoreSolvedTasks: number,
    scoreDate: Date,
    user: UserDto,
}