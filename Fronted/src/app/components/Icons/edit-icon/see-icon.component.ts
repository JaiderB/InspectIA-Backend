import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-see-icon',
  standalone: true,
  imports: [],
  templateUrl: './see-icon.component.html',
  styles: `
    :host{
      display:flex;
      align-items:center;
      --chain-border-color: none;
      --chain-back-fill-color: var(--inspectia-neutral-color-gray-70);
      --chain-fill-color: var(--inspectia-neutral-color-gray-60);
    }
  `
})
export class SeeIconComponent extends Icon{

}
