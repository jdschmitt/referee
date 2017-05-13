import { Injectable, EventEmitter } from '@angular/core';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map';
import { BaseService } from "./base.service";
import * as _ from 'underscore';

@Injectable()
export class SeasonsService extends BaseService {

  seasons: any[];
  seasonsChange: EventEmitter<any[]> = new EventEmitter<any[]>();

  URIs = {
    seasons: '/seasons',
  };

  getSeasons() {
    if (_.isUndefined(this.seasons))
      this.loadSeasons();

    return this.seasons;
  }

  private loadSeasons() {
    let httpReq = this.get(this.URIs.seasons).map((res:Response) => res.json());

    httpReq.subscribe(
        data => {
          this.seasons = data;
          this.seasonsChange.next(this.seasons);
        },
        error => console.log("Error calling /seasons: ", error._body)
    );
  }

}
