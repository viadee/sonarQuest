export interface StandardTask{
  id : number,
  title :  string ,
  status :  string ,
  gold : number,
  xp : number,
  quest : {
    id : number,
    title :  string ,
    story :  string ,
    status :  string ,
    gold : number,
    xp : number
  },
  world : null,
  taskType :  string ,
  participation : {
    quest: {
      id: number,
      title: string,
      story: string
    }
    developer: {
      id: number,
      username: string
    }
  },
   key : string,
   component :  string ,
   severity :  string ,
   type :  string ,
   debt : number,
   issueKey: string

}
