import { UserDto } from './UserDto';
import { EventDto } from './EventDto';

export interface EventUserDto{
    eventDtos: EventDto[],
    userDtos:  UserDto[]
}