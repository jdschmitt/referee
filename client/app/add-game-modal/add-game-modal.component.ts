import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';
import { GAME_TYPES_COLLECTION } from '../constants';
import { TeamsService } from '../services/teams.service';

export class AddGameDetails {
  constructor(
      public awayTeam: string,      // abbreviation
      public homeTeam: string,      // abbreviation
      public line: number,          // signed float
      public gameTime: string,      // MM-DD-YYYY hh:mm (see html template)
      public gameType: string       // abbreviation
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
  details: AddGameDetails = new AddGameDetails(null, null, null, null, null);
  allTeams: any[];
  awayTeams: any[];
  homeTeams: any[];

  constructor(
      private teamsService: TeamsService
  ) {
  }

  ngOnInit() {
    this.teamsService.getTeams().subscribe(
        data => {
          this.allTeams = data;
          this.awayTeams = this.allTeams.slice();
          this.homeTeams = this.allTeams.slice();
        },
        error => console.log("Error calling /teams: ", error)
    );
  }

  onTeamChange(which) {
    console.log(which + " team changed");
    // Remove other selection from possible values
    if (which === 'away') {
      // TODO Remove selected away team from home teams list
    } else if (which === 'home') {
      // TODO Remove selected home team from away teams list
    }
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

}
