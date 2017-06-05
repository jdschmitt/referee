import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map';
import {BaseService} from "./base.service";

@Injectable()
export class PlayersService extends BaseService{

  URIs = {
    mainPotRanking: '/mainPotRanking',
    secondPotRanking: '/secondPotRanking',
    players: '/players',
  };

  getMainPotRanking() {
    return this.get(this.URIs.mainPotRanking).map((res:Response) => res.json());
  }

  getSecondPotRanking() {
    return this.get(this.URIs.secondPotRanking).map((res:Response) => res.json());
  }

  playerSignup(player) {
    return this.post(this.URIs.players, player).map((res:Response) => res.json());
  }

}
