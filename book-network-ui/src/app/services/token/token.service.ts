import {Injectable} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  get token(): string {
    return localStorage.getItem('token') as string || '';
  }

  set token(token: string) {
    localStorage.setItem('token', token)
  }

  isTokenNotValid() {
    return !this.isTokenValid();
  }

  private isTokenValid() {
    const token = this.token;
    if (!token) {
      return false;
    }
    // decode token
    const jwtHelper = new JwtHelperService();
    // check expiry date
    const isExpired = jwtHelper.isTokenExpired(token);
    if (isExpired) {
      localStorage.clear()
      return false;
    }
    return true;
  }
}
