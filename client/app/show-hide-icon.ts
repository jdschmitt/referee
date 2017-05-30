import {Directive, HostBinding} from '@angular/core'

@Directive({ selector: '[show-hide-icon]'
})
export class ShowHideIcon
{
    base: string = 'glyphicon';
    hiddenIcon: string = 'glyphicon-eye-open';
    shownIcon: string = 'glyphicon-eye-close';
    @HostBinding() class: string;

    constructor(){;
        this.class = this.base + ' glyphicon-eye-open'
    }

    toggleIcon(): void {
        let icon = this.class.indexOf(this.hiddenIcon) >= 0 ? this.shownIcon : this.hiddenIcon;
        this.class = `${this.base} ${icon}`;
    }
}