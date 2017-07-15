import { Component } from '@angular/core';
import {SettingsService, Settings} from './services/settings.service';


/**
 * Base Component
 **/
@Component({
    selector : 'my-base',
    template: ``
})
export class BaseComponent {
    currentWeek: number;
    settings: Settings;
    _weekSub: any;
    _settingsSub: any;

    constructor(
        protected settingsService: SettingsService
    ) {
        this.currentWeek = this.settingsService.getCurrentWeek();
        this.settings = this.settingsService.getSettings();
        this._weekSub = this.settingsService.currentWeekChange.subscribe((value) => {
            this.currentWeek = value;
            this.onCurrentWeekChange();
        });

        this._settingsSub = this.settingsService.settingsLoaded.subscribe((value) => {
            this.settings = value;
            this.onSettingsLoaded();
        });
    }

    onDestroy() {
        this._weekSub.unsubscribe();
        this._settingsSub.unsubscribe();
    }

    // Placeholder for those child classes that don't define this.
    onCurrentWeekChange() {}

    onSettingsLoaded() {}
}
