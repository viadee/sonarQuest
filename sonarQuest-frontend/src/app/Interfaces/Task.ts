export interface World{
  id: number,
  name: string,
  project: string,
  active: boolean
}

export interface Task{
  id : number,
  title :  string ,
  status :  string ,
  gold : number,
  xp : number,
  key : string,
  component :  string ,
  severity :  string ,
  type :  string ,
  debt : number,
  message: string,
  world: World,
  issueKey: string
}
