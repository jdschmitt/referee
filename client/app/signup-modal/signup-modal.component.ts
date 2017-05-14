import {Component, OnInit, ViewChild, ViewEncapsulation, Input, EventEmitter} from '@angular/core';
import { BaseComponent } from '../base.component';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';
import { SettingsService } from '../services/settings.service';

export class PlayerSignup {
  constructor(
      public firstName: string,
      public lastName: string,
      public email: string,
      public password1: string,
      public password2: string,
      public defaultPick: string
  ) {}
}

@Component({
  selector: 'signup-modal',
  templateUrl: './signup-modal.component.html',
  styleUrls: ['./signup-modal.component.css'],

  encapsulation: ViewEncapsulation.None
})
export class SignupModalComponent extends BaseComponent implements OnInit {

  @ViewChild(ModalComponent) modal: ModalComponent;
  player: PlayerSignup = new PlayerSignup(null, null, null, null, null, null);

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

}
