import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map';
import {BaseService} from "./base.service";

@Injectable()
export class GamesService extends BaseService {

  URIs = {
    games: '/games',
  };

  getGamesForWeek(week) {
    this.logger.log("Getting games for week" + week);
    // TODO should introduce a cache here
    return this.get(this.URIs.games, {"week": week}).map((res:Response) => res.json());
  }

  addGame(game) {
    return this.post(this.URIs.games, game).map((res:Response) => res.json());
  }

}
