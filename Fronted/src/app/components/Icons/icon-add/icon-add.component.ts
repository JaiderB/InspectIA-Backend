import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-icon-add',
  standalone: true,
  imports: [],
  templateUrl: './icon-add.component.svg',
  styles: `
    :host{
      display:flex;
      align-items:center;
    }
  `
})
export class IconAddComponent extends Icon{

}
