import { Component, OnInit } from '@angular/core';
import {BaseComponent} from '../base.component';
import {SettingsService} from "../services/settings.service";

@Component({
  selector: 'app-my-picks',
  templateUrl: './my-picks.component.html',
  styleUrls: ['./my-picks.component.css']
})
export class MyPicksComponent extends BaseComponent implements OnInit {

  constructor(
    protected settingsService: SettingsService
  ) {
    super(settingsService);
  }

  ngOnInit() {
  }

}
