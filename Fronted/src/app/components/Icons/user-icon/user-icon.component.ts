import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-user-icon',
  standalone: true,
  imports: [],
  templateUrl: './user-icon.component.svg',
  styles: `
    :host{
      display:flex;
      align-items:center;
    }
  `
})
export class UserIconComponent extends Icon{

}
