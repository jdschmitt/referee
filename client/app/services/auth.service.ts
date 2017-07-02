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

  authToken: string = null;
  authTokenChange: EventEmitter<string> = new EventEmitter<string>();
  URIs = {
    auth: "/auth"
  };

  login(creds: LoginCreds, success?: (token: string) => void, failure?: (error: Response) => void) {
    let userPass: string = creds.btoa();
    let headers = {
      'Authorization': 'Basic ' + userPass
    };
    console.log('spinner start');
    let call = this.get(this.URIs.auth, {}, headers).map((res:Response) => res.json());
    call.subscribe(
        data => {
          this.authToken = data.token;
          this.authTokenChange.next(this.authToken);
          success(data.token);
          console.log('spinner end');
        },
        error => failure(error)
    );
  }

}
