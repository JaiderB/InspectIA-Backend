import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-error-icon',
  standalone: true,
  imports: [],
  templateUrl: './error-icon.component.svg',
  styles: `
    :host{
      display:flex;
      align-items:center;
    }
  `
})
export class ErrorIconComponent extends Icon {

}
