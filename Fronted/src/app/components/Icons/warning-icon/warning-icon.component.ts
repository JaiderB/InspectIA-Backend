import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-warning-icon',
  standalone: true,
  imports: [],
  templateUrl: './warning-icon.component.svg',
  styles: `
    :host{
      display:flex;
      align-items:center;
    }
  `
})
export class WarningIconComponent extends Icon {

}
