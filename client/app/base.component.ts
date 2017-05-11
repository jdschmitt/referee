import { Component } from '@angular/core';
import { SettingsService } from './services/settings.service';


/**
 * Base Component
 **/
@Component({
    selector : 'my-base',
    template: ``
})
export class BaseComponent {
    currentWeek: number;
    _subscription: any;

    constructor(
        protected settingsService: SettingsService
    ) {
        this.currentWeek = this.settingsService.getCurrentWeek();
        this._subscription = this.settingsService.currentWeekChange.subscribe((value) => {
            this.currentWeek = value;
            this.onCurrentWeekChange();
        });
    }

    onDestroy() {
        this._subscription.unsubscribe();
    }

    // Placeholder for those child classes that don't define this.
    onCurrentWeekChange() {}
}
