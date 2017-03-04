import { Injectable } from '@angular/core';
import {LoginCreds} from "../login-modal/login-modal.component";

export class AuthResponse {
  success: boolean;
  token: string;
  error: string;
}

@Injectable()
export class AuthService {

  loggedIn = false;

  isLoggedIn() {
    return this.loggedIn;
  }

  login(creds: LoginCreds): AuthResponse {
    // TODO Actually authenticate against API
    // Probably return a custom object that can communicate token, errors, etc.
    console.log('Logging in ' + creds.username);
    this.loggedIn = true;
    return {
      success: this.loggedIn,
      token: 'abcdef1234567890',
      error: null
    };
  }

}
