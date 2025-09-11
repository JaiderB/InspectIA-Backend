import { Component, HostBinding, Input } from '@angular/core';
import { InspectiaTextComponent } from '@components/inspectia-text/inspectia-text.component';

@Component({
  selector: 'app-value-row',
  standalone: true,
  imports: [InspectiaTextComponent],
  templateUrl: './value-row.component.html',
  styleUrl: './value-row.component.css'
})
export class ValueRowComponent {

  @Input() value = "";
  @Input() size = "33.33%"


  @HostBinding('style.--size')
  get sizeRow() {
    return this.size;
  }


}
