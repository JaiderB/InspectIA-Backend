import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-delete-icon',
  standalone: true,
  imports: [],
  templateUrl: './delete-icon.component.html',
  styles: `
    :host{
      display:flex;
      align-items:center;
      --chain-border-color: none;
      --chain-back-fill-color: var(--inspectia-neutral-color-gray-60);
      --chain-fill-color: var(--inspectia-neutral-color-gray-70);
    }
  `
})
export class DeleteIconComponent extends Icon {

}
