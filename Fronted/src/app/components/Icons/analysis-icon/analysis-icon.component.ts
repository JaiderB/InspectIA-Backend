import { Component } from '@angular/core';
import { Icon } from '@icons/icon/Icon';

@Component({
  selector: 'app-analysis-icon',
  standalone: true,
  imports: [],
  templateUrl: './analysis-icon.component.svg',
  styles: `
        :host{
      display:flex;
      align-items:center;
    }
  `
})
export class AnalysisIconComponent extends Icon {

}
