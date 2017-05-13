import { Component, OnInit } from '@angular/core';
import { BaseComponent } from '../base.component';
import { SeasonsService } from '../services/seasons.service';
import { SettingsService } from '../services/settings.service';
import * as moment from 'moment';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent extends BaseComponent implements OnInit {

  seasons: any[];
  _subscription: any;

  constructor(
      protected seasonsService: SeasonsService,
      protected settingsService: SettingsService
  ) {
    super(settingsService);
  }

  ngOnInit() {
    this.seasons = this.seasonsService.getSeasons();
    this._subscription = this.seasonsService.seasonsChange.subscribe((value) => {
      this.seasons = value;
    })
  }

  seasonDescription(season) {
    let openYear: number = moment(season.firstRegularGameDate, "MM-DD-YYYY HH:mm").year();
    let closeYear: number = moment(season.superBowlDate, "MM-DD-YYYY HH:mm").year();

    return openYear + " / " + closeYear + " NFL season";
  }

  displayDate(date) {
    let formatted = moment(date, "MM-DD-YYYY HH:mm");
    return formatted.format("MMMM DD, YYYY");
  }

  openAddSeasonModal() {

  }

}
