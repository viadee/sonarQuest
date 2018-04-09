export interface SpecialTask {
  id: number,
  title: string,
  status: string,
  gold: number,
  xp: number,
  quest: {
    id: number,
    title: string,
    story: string,
    status: string,
    gold: number,
    xp: number
  },
  world: null,
  taskType: string,
  participation: {
    quest: {
      id: number,
      title: string,
      story: string
    }
    developer: {
      id: number,
      username: string
    }
  }
  message: string
}
