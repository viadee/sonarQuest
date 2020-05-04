import { UserDto } from './UserDto';

export interface RaidLeaderboard {
    scoreGold: number,
    scoreXp: number,
    scoreDate: Date,
    user: UserDto,
}