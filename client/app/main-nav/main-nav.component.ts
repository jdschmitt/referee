import { Component, ViewChild, OnInit } from '@angular/core';
import { LoginModalComponent } from '../login-modal/login-modal.component';
import { SignUpModalComponent } from '../signup-modal/signup-modal.component';
import { AuthService } from "../services/auth.service";

@Component({
  selector: 'main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: [`./main-nav.component.css`]
})

export class MainNavComponent {

  @ViewChild(LoginModalComponent) loginModal: LoginModalComponent;
  @ViewChild(SignUpModalComponent) signupModal: SignUpModalComponent;
  _authTokenSubscription: any;

  constructor(
      private authService: AuthService
  ) {
    this._authTokenSubscription = this.authService.authTokenChange.subscribe((token) => {
      this.authTokenChanged(token);
    });
  }

  openLoginModal() {
    this.loginModal.open();
  }

  openSignupModal() {
    this.signupModal.open();
  }

  authTokenChanged(token) {
    console.log('Update nav bar to reflect authentication state: ' + token)
  }
}
