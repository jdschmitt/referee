import { Injectable, EventEmitter } from '@angular/core';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map';
import { BaseService } from "./base.service";
import * as _ from 'underscore';

@Injectable()
export class SettingsService extends BaseService {

  currentWeek: number;
  currentWeekChange: EventEmitter<number> = new EventEmitter<number>();

  URIs = {
    currentWeek: '/currentWeek',
    settings: '/settings'
  };

  getCurrentWeek() {
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

  getSettings() {
    return this.get(this.URIs.settings).map((res:Response) => res.json());
  }

}
