import {Directive, HostBinding} from '@angular/core'

@Directive({ selector: '[show-hide-input]'
})
export class ShowHideInput
{
    @HostBinding() type: string;

    constructor(){
        this.type='password';
    }

    toggleType(): void {
        this.type = this.type === 'password' ? 'text' : 'password';
    }

}