import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map';
import {BaseService} from "./base.service";
import {retry} from "rxjs/operator/retry";

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

  allPlayers() {
    return this.get(this.URIs.players).map((res:Response) => res.json());
  }

  deletePlayer(id: number) {
    return this.delete(`${this.URIs.players}/${id}`).map((res:Response) => res.json());
  }

}
