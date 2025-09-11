import { Component } from '@angular/core';
import { Icon } from '../icon/Icon';

@Component({
  selector: 'app-delete-modal-icon',
  standalone: true,
  imports: [],
  templateUrl: './delete-modal-icon.component.html',
  styles: `
        :host{
      display:flex;
      align-items:center;
    }
  `
})
export class DeleteModalIconComponent extends Icon {

}
