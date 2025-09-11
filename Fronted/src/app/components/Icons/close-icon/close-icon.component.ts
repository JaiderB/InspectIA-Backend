import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-close-icon',
  standalone: true,
  imports: [],
  templateUrl: './close-icon.component.html',
  styles: `
      :host{
      display:flex;
      align-items:center;
      width:min-content;
      cursor: pointer;
    }

    rect{
      fill: transparent;
    }
    svg:hover rect{
      fill: var(--inspectia-neutral-color-gray-70);
    }
  `
})
export class CloseIconComponent extends Icon{

}
