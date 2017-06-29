import { Component, OnInit } from '@angular/core';
import { PlayersService } from '../services/players.service';
import { BaseComponent } from "../base.component";
import { SettingsService } from "../services/settings.service";
import { DEFAULT_PICKS_COLLECTION } from "../constants";
import * as _ from 'underscore';

@Component({
  selector: 'app-player-admin',
  templateUrl: './player-admin.component.html',
  styleUrls: ['./player-admin.component.css']
})
export class PlayerAdminComponent extends BaseComponent implements OnInit {

  players: any[];
  picks = DEFAULT_PICKS_COLLECTION;

  constructor(
      protected settingsService: SettingsService,
      protected playersService: PlayersService
  ) {
    super(settingsService);
  }

  ngOnInit() {
    this.loadPlayers();
  }

  loadPlayers() {
    this.playersService.allPlayers().subscribe(
        data => {
          this.players = data;
        },
        error => console.log("Error calling /players: ", error)
    );
  }

  deletePlayer(id: number) {
    this.playersService.deletePlayer(id).subscribe(
        data => this.loadPlayers(),
        error => console.log(`Error deleting player ${id}`, error)
    );
  }

  pickDisplay(id: string) {
    return _.find(this.picks, (p) => {return p.id == id;}).display;
  }

}
