import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';
import { GAME_TYPES_COLLECTION } from '../constants';

export class AddGameDetails {
  constructor(
      public awayTeam: string,      // abbreviation
      public homeTeam: string,      // abbreviation
      public line: number,      // signed float
      public gameTime: string,  //
      public gameType: string
  ) { }
}

@Component({
  selector: 'add-game-modal',
  templateUrl: './add-game-modal.component.html',
  styleUrls: ['./add-game-modal.component.css', '../forms.css'],

  encapsulation: ViewEncapsulation.None
})
export class AddGameModalComponent implements OnInit {

  @ViewChild(ModalComponent) modal: ModalComponent;
  gameTypes = GAME_TYPES_COLLECTION;
  details = new AddGameDetails(null, null, null, null, null);

  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit() {

  }

  closed() {
  }

  dismissed() {
  }

  opened() {
  }

  open() {
    return this.modal.open();
  }

  onSubmit() {
    console.log('Game time: ' + this.details.gameTime);
    return this.modal.close();
  }

  getGameTypes() {
    return this.gameTypes;
  }
}
