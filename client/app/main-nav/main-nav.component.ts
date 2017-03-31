import {Component, ViewChild, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";
import { LoginModalComponent } from '../login-modal/login-modal.component';

@Component({
  selector: 'main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: [`./main-nav.component.css`]
})

export class MainNavComponent implements OnInit {

  @ViewChild(LoginModalComponent) loginModal: LoginModalComponent;

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  openModal() {
    this.loginModal.open();
  }

  ngOnInit() {
    console.log("Init main-nav");

    // TODO If not authenticated, redirect to /login
    // TODO Uncomment when it's time to implement auth
    /*
     if (!this.authService.isLoggedIn()) {
     this.router.navigate(['/login']);
     }
     */
  }
}
