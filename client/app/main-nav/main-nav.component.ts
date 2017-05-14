import {Component, ViewChild, OnInit} from '@angular/core';
import { LoginModalComponent } from '../login-modal/login-modal.component';
import { SignupModalComponent } from '../signup-modal/signup-modal.component';

@Component({
  selector: 'main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: [`./main-nav.component.css`]
})

export class MainNavComponent implements OnInit {

  @ViewChild(LoginModalComponent) loginModal: LoginModalComponent;
  @ViewChild(SignupModalComponent) signupMOdal: SignupModalComponent;

  constructor() { }

  openLoginModal() {
    this.loginModal.open();
  }

  openSignupModal() {
    this.signupMOdal.open();
  }

  ngOnInit() {
  }
}
