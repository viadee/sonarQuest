import { AdventureState } from "./AdventureState";

export interface Adventure {
  id: number,
  title: string,
  story: string,
  status: AdventureState,
  gold: number,
  xp: number,
  quests: any,
  users: any
}
