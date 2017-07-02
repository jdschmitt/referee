import { Component, ViewChild, OnInit, ViewEncapsulation } from '@angular/core';
import { AuthService } from "../services/auth.service";
import { Router } from "@angular/router";
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';
import { Response } from "@angular/http";

export class LoginCreds {
  constructor(
    public username: string,
    public password: string,
    public token: string
  ) { }

  btoa() {
    return window.btoa(this.username + ':' + this.password);
  }
}

@Component({
  selector: 'login-modal',
  templateUrl: './login-modal.component.html',
  styleUrls: [`./login-modal.component.css`,'../forms.css'],

  encapsulation: ViewEncapsulation.None
})
export class LoginModalComponent implements OnInit {

  @ViewChild(ModalComponent) modal: ModalComponent;
  creds: LoginCreds = new LoginCreds(null,null,null);
  output: string;
  selected: string;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    console.log("Init login modal");
    // if (this.authService.isLoggedIn()) {
    //   this.router.navigate([''])
    // }
  }

  closed() {
    this.output = '(closed) ' + this.selected;
  }

  dismissed() {
    this.output = '(dismissed)';
  }

  opened() {
    this.output = '(opened)';
    this.creds = new LoginCreds(null,null,null);
  }

  navigate() {
    this.router.navigateByUrl('/hello');
  }

  open() {
    this.modal.open();
  }

  onSubmit() {
    this.authService.login(this.creds,
        token => {
          this.loginSuccess(token);
        },
        error => {
          this.loginFailure(error);
        }
    );
  }

  loginSuccess(token: string) {
    console.log('Successful login: ' + token)
    this.modal.close();
  }

  loginFailure(error: Response) {
    if (error.status === 401) {
      // Unauthorized
      console.log('Authentication failed. Please check username / password and try again.');
    } else {
      console.log('Failed to login: ' + error);
    }
  }
}
