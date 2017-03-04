import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SettingsService} from "../services/settings.service";
import {PlayersService} from "../services/players.service";

@Component({
  selector: 'second-pot',
  templateUrl: './second-pot.component.html',
  styleUrls: [`./second-pot.component.css`]
})
export class SecondPotComponent implements OnInit {

  currentWeek: number = -1;
  players: any[];

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

    this.playersService.getSecondPotRanking().subscribe(
      data => this.players = data,
      error => console.log("Error calling /secondPotRanking: ", error)
    );
  }
}
