import {Component, OnInit, ViewChild, ViewEncapsulation, EventEmitter} from '@angular/core';
import { BaseComponent } from '../base.component';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';
import { GAME_TYPES_COLLECTION } from '../constants';
import { SettingsService } from '../services/settings.service';
import { TeamsService } from '../services/teams.service';
import * as _ from 'underscore';

export class AddGameDetails {
  constructor(
      public awayTeam: string,      // abbreviation
      public homeTeam: string,      // abbreviation
      public line: number,          // signed float
      public gameTime: any,         // MM-DD-YYYY hh:mm (see html template)
      public gameType: string       // abbreviation
  ) { }
}

@Component({
  selector: 'add-game-modal',
  templateUrl: './add-game-modal.component.html',
  styleUrls: ['./add-game-modal.component.css', '../forms.css'],

  encapsulation: ViewEncapsulation.None
})
export class AddGameModalComponent extends BaseComponent implements OnInit {

  @ViewChild(ModalComponent) modal: ModalComponent;
  gameTypes = GAME_TYPES_COLLECTION;
  details: AddGameDetails = new AddGameDetails(null, null, null, null, null);
  allTeams: any[];
  awayTeams: any[];
  homeTeams: any[];

  onClose: EventEmitter<any>;

  constructor(
      protected settingsService: SettingsService,
      private teamsService: TeamsService
  ) {
    super(settingsService);
  }

  ngOnInit() {
    this.onClose = this.modal.onClose;
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
    // TODO do some validation to prevent the same team to be chosen for away and home.
  }

  open() {
    return this.modal.open();
  }

  closed() {
    // return this.modal.close();
  }

  getGame() {
    let awayTeam = _.find(this.allTeams, obj => obj.id === parseInt(this.details.awayTeam));
    let homeTeam = _.find(this.allTeams, obj => obj.id === parseInt(this.details.homeTeam));
    return {
      "awayTeam": awayTeam,
      "homeTeam": homeTeam,
      "line": this.details.line,
      "gameTime": this.details.gameTime.toString(),
      "gameType": this.details.gameType,
      "weekNumber": this.currentWeek,
      "version": 1,
      "seasonId": 1
    }

  }

}
