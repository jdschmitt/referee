import { Component, ViewChild, OnInit } from '@angular/core';
import { BaseComponent } from '../base.component';
import { AddGameModalComponent } from '../add-game-modal/add-game-modal.component';
import { GamesService } from '../services/games.service';
import { SettingsService } from '../services/settings.service';
import * as _ from 'underscore';

@Component({
  selector: 'app-game-admin',
  templateUrl: './game-admin.component.html',
  styleUrls: ['./game-admin.component.css']
})
export class GameAdminComponent extends BaseComponent implements OnInit {

  @ViewChild('addGameModal') addGameModal: AddGameModalComponent;
  games: any[];
  _onCloseSub: any;

  constructor(
      private gamesService: GamesService,
      protected settingsService: SettingsService,
  ) {
    super(settingsService);
  }

  ngOnInit() {
    this.loadGames();
  }

  onCurrentWeekChange() {
    this.loadGames();
  }

  openAddGameModal() {
    this._onCloseSub = this.addGameModal.onClose.subscribe(() => {
      let game = this.addGameModal.getGame();
      this.addGame(game);
      this._onCloseSub.unsubscribe();
    });
    return this.addGameModal.open();
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

  addGame(game) {
    this.gamesService.addGame(game).subscribe(
        complete => this.loadGames(),
        error => console.log("Failed to save game: " + error._body)
    );
  }

}
