import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { BaseComponent } from '../base.component';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';
import { SettingsService } from '../services/settings.service';
import { ShowHideIcon } from '../show-hide-icon';
import { ShowHideInput } from '../show-hide-input';

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

  @ViewChild(ShowHideIcon) icon: ShowHideIcon;
  @ViewChild(ShowHideInput) input: ShowHideInput;
  @ViewChild(ModalComponent) modal: ModalComponent;
  player: PlayerSignup = new PlayerSignup();

  constructor(
      protected settingsService: SettingsService
  ) {
    super(settingsService);
  }

  ngOnInit() {
  }

  open() {
    return this.modal.open();
  }

  signup() {
    // TODO attempt to create new user via API
  }

  toggleMask() {
    this.input.toggleType();
    this.icon.toggleIcon();
  }

}
