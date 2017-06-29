import { Injectable } from '@angular/core';
import { Http, URLSearchParams, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';
import { environment } from '../../environments/environment';
import { Logger } from './logger.service';
import * as _ from 'underscore';

@Injectable()
export class BaseService {

  baseURL: string = environment.baseURL;
  headers = new Headers({ 'Content-Type': 'application/json' });
  options = new RequestOptions({ headers: this.headers });

  constructor (
    protected http: Http,
    protected logger: Logger
  ) {}

  private getURL(uri: string) {
    return this.baseURL + uri;
  }

  // Thinking that doing these here may allow caching to be introduced easier in one place
  get(uri: string, paramsMap = {}) {
    let params: URLSearchParams = new URLSearchParams();
    _.each(paramsMap, (v: string, k: string, m) => params.set(k, v));
    return this.http.get(this.getURL(uri), {
      search: params
    });
  }

  post(uri: string, json = {}) {
    return this.http.post(this.getURL(uri), JSON.stringify(json), this.options);
  }

  delete(uri: string, paramsMap = {}) {
    let params: URLSearchParams = new URLSearchParams();
    _.each(paramsMap, (v: string, k: string, m) => params.set(k, v));
    return this.http.delete(this.getURL(uri), {
      search: params
    });
  }

}
