import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { BaseComponent } from '../base.component';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';
import { SettingsService } from '../services/settings.service';
import { PlayersService } from '../services/players.service';
import { NgForm } from "@angular/forms";

export class PlayerSignup {
  constructor(
      public leaguePass: string = null,
      public firstName: string = null,
      public lastName: string = null,
      public email: string = null,
      public password: string = null,
      public defaultPick: string = ""
  ) {}
}

@Component({
  selector: 'signup-modal',
  templateUrl: './signup-modal.component.html',
  styleUrls: ['./signup-modal.component.css'],

  encapsulation: ViewEncapsulation.None
})
export class SignUpModalComponent extends BaseComponent implements OnInit {

  @ViewChild(ModalComponent) modal: ModalComponent;
  @ViewChild('modalForm') public signUpForm: NgForm;
  player: PlayerSignup = new PlayerSignup();

  constructor(
      protected settingsService: SettingsService,
      protected playersService: PlayersService,
  ) {
    super(settingsService);
  }

  ngOnInit() {
  }

  open() {
    return this.modal.open();
  }

  close() {
    this.signUpForm.reset();
    return this.modal.close();
  }

  signup() {
    let self = this;
    this.playersService.playerSignup(this.player).subscribe(
        complete => {
          console.log("Signup successful");
          // this.close();
          // Auto sign in?
        },
        error => console.log("Failed to sign up: " + error._body)
    );
  }

}
