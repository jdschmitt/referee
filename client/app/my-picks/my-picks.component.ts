import { Component, OnInit } from '@angular/core';
import { BaseComponent } from '../base.component';
import { GamesService } from '../services/games.service';
import { SettingsService } from "../services/settings.service";
import * as _ from 'underscore';

@Component({
  selector: 'app-my-picks',
  templateUrl: './my-picks.component.html',
  styleUrls: ['./my-picks.component.css']
})
export class MyPicksComponent extends BaseComponent implements OnInit {

  constructor(
    protected settingsService: SettingsService,
    private gamesService: GamesService
  ) {
    super(settingsService);
  }

  games: any[];

  ngOnInit() {
    this.loadGames();
  }

  // TODO probably don't really need this but need to adjust when user changes which week they want to view
  onCurrentWeekChange() {
    this.loadGames();
  }

  loadGames() {
    if (_.isUndefined(this.currentWeek))
      return;

    this.gamesService.getGamesForWeek(this.currentWeek).subscribe(
      data => {
        this.games = data;
      },
      error => console.log("Error calling /games: ", error)
    );
  }

  submitPicks() {
    console.log("submitting picks");
  }

}
