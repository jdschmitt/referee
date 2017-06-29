import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { BaseComponent } from '../base.component';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';
import { SettingsService } from '../services/settings.service';
import { PlayersService } from '../services/players.service';
import { NgForm } from "@angular/forms";
import { DEFAULT_PICKS_COLLECTION } from '../constants';

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
  picks = DEFAULT_PICKS_COLLECTION;

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
    this.playersService.playerSignup(this.player).subscribe(
        complete => {
          this.modal.dismiss();
        },
        error => console.log("Failed to sign up: " + error._body)
    );
  }

}
