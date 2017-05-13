import { Component, OnInit } from '@angular/core';
import { BaseComponent } from '../base.component';
import { SettingsService } from '../services/settings.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent extends BaseComponent implements OnInit {

  seasons: any[];

  constructor(
      protected settingsService: SettingsService
  ) {
    super(settingsService);
  }

  ngOnInit() {
  }

  openAddSeasonModal() {

  }

}
