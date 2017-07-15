import { Injectable, EventEmitter } from '@angular/core';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map';
import { BaseService } from "./base.service";
import * as _ from 'underscore';

export class Settings {
  constructor(
    public commishNote: string,
    public secondPotStartWeek: number,
    public winnersPerWeek: number,
    public seasonId: number,
    public leaguePassword: string
  ) {}
}

@Injectable()
export class SettingsService extends BaseService {

  currentWeek: number;
  settings: Settings;
  currentWeekChange: EventEmitter<number> = new EventEmitter<number>();
  settingsLoaded: EventEmitter<Settings> = new EventEmitter<Settings>();

  URIs = {
    currentWeek: '/currentWeek',
    settings: '/settings'
  };

  getCurrentWeek(): number {
    if (_.isUndefined(this.currentWeek))
      this.loadCurrentWeekFromServer();

    return this.currentWeek;
  }

  private loadCurrentWeekFromServer() {
    let httpReq = this.get(this.URIs.currentWeek).map((res:Response) => res.json());

    httpReq.subscribe(
        data => {
          this.currentWeek = data.currentWeek;
          this.currentWeekChange.next(this.currentWeek);
        },
        error => console.log("Error calling /currentWeek: ", error._body)
    );
  }

  private loadSettingsFromServer() {
    let httpReq = this.get(this.URIs.settings).map((res:Response) => res.json());

    httpReq.subscribe(
      data => {
        this.settings = <Settings>data;
        this.settingsLoaded.next(this.settings);
      },
      error => console.log("Error calling /settings: ", error._body)
    );
  }

  getSettings(): Settings {
    if (_.isUndefined(this.settings))
      this.loadSettingsFromServer();

    return this.settings;
  }

  getSeasonId(): number {
    if (_.isUndefined(this.settings))
      this.loadSettingsFromServer();

    return this.settings.seasonId;
  }

}
