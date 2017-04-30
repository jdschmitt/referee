import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map';
import {BaseService} from "./base.service";

@Injectable()
export class TeamsService extends BaseService {

  URIs = {
    teams: '/teams',
  };

  getTeams() {
    // TODO should introduce a cache here
    return this.get(this.URIs.teams).map((res:Response) => res.json());
  }

}
