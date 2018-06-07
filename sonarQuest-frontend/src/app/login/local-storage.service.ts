import {Injectable} from '@angular/core';
import {Token} from './token';


@Injectable()
export class LocalStorageService {

  private KEY_TOKEN = 'SONAR-QUEST-TKN';

  constructor() {
  }

  public saveJWTToken(token: Token): void {
    localStorage.setItem(this.KEY_TOKEN, JSON.stringify(token));
  }

  public getJWTToken(): Token {
    let token: Token = null;

    const jsonObj = JSON.parse(localStorage.getItem(this.KEY_TOKEN));
    if (jsonObj) {
      token = new Token(jsonObj.jwt, jsonObj.expires_at);
    }
    return token;
  }

  public removeJWTToken(): void {
    localStorage.removeItem(this.KEY_TOKEN);
  }
}
