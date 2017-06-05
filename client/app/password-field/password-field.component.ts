import {Component, Input, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'password-field',
  templateUrl: './password-field.component.html',
  styleUrls: ['./password-field.component.css']
})
export class PasswordFieldComponent {

  @Input() public name: string;
  @Input() public value: string;
  @Output() public valueChange:EventEmitter<String> = new EventEmitter<String>();

  type: string = 'password';
  iconClass: string = 'glyphicon-eye-open';

  showPassword() {
    this.iconClass = 'glyphicon-eye-close';
    this.type = 'text';
  }

  hidePassword() {
    this.iconClass = 'glyphicon-eye-open';
    this.type = 'password';
  }

  toggleMask() {
    this.iconClass = this.iconClass === 'glyphicon-eye-open' ? 'glyphicon-eye-close' : 'glyphicon-eye-open';
    this.type = this.type === 'password' ? 'text' : 'password';
  }

}
