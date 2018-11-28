import { AdventureState } from "./AdventureState";

export interface Adventure {
  id: number,
  title: string,
  story: string,
  status: AdventureState,
  visible: boolean,
  gold: number,
  xp: number,
  quests: any,
  users: any,
  startdate: Date,
  enddate: Date
}
