import * as moment from 'moment';

export class Token {

  constructor(private jwt: string, private expires_at: number) {
  }

  public hasExpired(): boolean {
    return moment().isBefore(this.expires_at);
  }

  public getJwt(): string {
    return this.jwt;
  }

}
