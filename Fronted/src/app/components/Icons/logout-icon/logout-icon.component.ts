import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-logout-icon',
  standalone: true,
  imports: [],
  templateUrl: './logout-icon.component.svg',
  styles: `
    :host{
      display:flex;
      align-items:center;
    }
  `
})
export class LogoutIconComponent extends Icon{

}
