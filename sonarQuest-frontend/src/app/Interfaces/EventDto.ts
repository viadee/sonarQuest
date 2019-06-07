export enum EventState {
    NEW_MEMBER = 'NEW_MEMBER',
	CREATED    = 'CREATED',
	SOLVED     = 'SOLVED',
	NEW_ITEM   = 'NEW_ITEM'
}

export interface EventDto {
    id: number,
    type: string,
    type_id: number,
    title: string,
    story: string,
    state: EventState,
    image: any,
    worldId: number,
    timestamp: Date,
    userId: number,
    headline: string
}
