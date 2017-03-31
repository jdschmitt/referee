import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SettingsService} from "../services/settings.service";
import {PlayersService} from "../services/players.service";

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: [`./home.component.css`]
})
export class HomeComponent implements OnInit {

  currentWeek: number = -1;
  players: any[];
  commishNote: string;

  constructor(
    private router: Router,
    private settingsService: SettingsService,
    private playersService: PlayersService
  ) { }

  ngOnInit() {
    this.settingsService.getCurrentWeek().subscribe(
      data => this.currentWeek = data.currentWeek,
      error => console.log("Error calling /currentWeek: ", error)
    );

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
