import {BaseService} from "./base.service";
import {Injectable, EventEmitter} from '@angular/core';
import { LoginCreds } from "../login-modal/login-modal.component";
import { Response } from '@angular/http';
import * as _ from 'underscore';

export class AuthResponse {
  success: boolean;
  token: string;
  error: string;
}

@Injectable()
export class AuthService extends BaseService {

  private _isAdmin: boolean = false;
  private _authToken: string = null;
  authTokenChange: EventEmitter<string> = new EventEmitter<string>();
  URIs = {
    login: "/login",
    logout: "/logout"
  };

  login(creds: LoginCreds, success?: (token: string) => void, failure?: (error: Response) => void) {
    let userPass: string = creds.btoa();
    let headers = {
      'Authorization': 'Basic ' + userPass
    };
    console.log('login spinner start');
    let call = this.post(this.URIs.login, {}, headers).map((res:Response) => res.json());
    call.subscribe(
      data => {
        // TODO store in a cookie for when they return?
        this.setAuthToken(data.token);
        if (success) {
          success(data.token);
        }
      },
      error => {
        if (failure) {
          failure(error);
        }
      },
      () => console.log('login spinner end')
    );
  }

  logout(success?: () => void, failure?: (error: Response) => void) {
    console.log('logout spinner start');
    let call = this.post(this.URIs.logout, {token: this._authToken}).map((res:Response) => res.json());
    call.subscribe(
      () => {
        this.setAuthToken(null);
        if (success) {
          success();
        }
      },
      error => {
        if (failure) {
          failure(error)
        }
      },
      () => console.log('logout spinner end')
    );
  }

  setAuthToken(token) {
    this._authToken = token;
    this.authTokenChange.next(token);
  }

  authToken(): string {
    return this._authToken;
  }

  isAdmin(): boolean {
    return this._isAdmin;
  }

  isLoggedIn(): boolean {
    return !_.isNull(this._authToken);
  }

}
