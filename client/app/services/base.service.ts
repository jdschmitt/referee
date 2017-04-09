/**
 * Created by jason on 2/21/17.
 */
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import { environment } from '../../environments/environment';

@Injectable()
export class BaseService {

  baseURL: string = environment.baseURL;

  constructor (
    protected http: Http
  ) {}

  private getURL(uri: string) {
    return this.baseURL + uri;
  }

  // Thinking that doing these here may allow caching to be introduced easier in one place
  get(uri: string) {
    return this.http.get(this.getURL(uri));
  }

}
