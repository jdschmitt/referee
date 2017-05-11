import {Component, OnInit} from '@angular/core';
import {BaseComponent} from '../base.component';
import {SettingsService} from "../services/settings.service";
import {PlayersService} from "../services/players.service";

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: [`./home.component.css`]
})
export class HomeComponent extends BaseComponent implements OnInit {

  players: any[];
  commishNote: string;

  constructor(
    protected settingsService: SettingsService,
    private playersService: PlayersService
  ) {
    super(settingsService);
  }

  ngOnInit() {
    this.playersService.getMainPotRanking().subscribe(
      data => this.players = data,
      error => console.log("Error calling /mainPotRanking: ", error)
    );

    this.settingsService.getSettings().subscribe(
      data => this.commishNote = data.commishNote,
      error => console.log("Error calling /settings: ", error)
    );
  }

}
