import { Component, OnInit } from '@angular/core';
import { BaseComponent } from '../base.component';
import { PlayersService } from "../services/players.service";
import { SettingsService } from "../services/settings.service";

@Component({
  selector: 'second-pot',
  templateUrl: './second-pot.component.html',
  styleUrls: [`./second-pot.component.css`]
})
export class SecondPotComponent extends BaseComponent implements OnInit {

  players: any[];

  constructor(
    private playersService: PlayersService,
    protected settingsService: SettingsService
  ) {
    super(settingsService);
  }

  ngOnInit() {
    this.playersService.getSecondPotRanking().subscribe(
      data => this.players = data,
      error => console.log("Error calling /secondPotRanking: ", error)
    );
  }

}
