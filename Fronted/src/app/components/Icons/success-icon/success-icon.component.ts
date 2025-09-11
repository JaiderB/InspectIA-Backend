import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-success-icon',
  standalone: true,
  imports: [],
  templateUrl: './success-icon.component.svg',
  styles: `
    :host{
      display:flex;
      align-items:center;
    }
  `
})
export class SuccessIconComponent extends Icon {

}
