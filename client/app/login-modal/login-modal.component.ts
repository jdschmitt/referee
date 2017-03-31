import {Component, ViewChild, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';

export class LoginCreds {
  constructor(
    public username: string,
    public password: string,
    public token: string
  ) { }
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
    // TODO Authenticate (get token)
    let resp = this.authService.login(this.creds);
    if (resp.success) {
      this.creds.token = resp.token;
      // TODO refresh or something to reflect logged in state
      this.modal.close();
    }

    // TODO display login error
    return false;
  }
}
