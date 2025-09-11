import { Component, EventEmitter, Input, Output } from '@angular/core';
import { InspectiaTextComponent } from '@components/inspectia-text/inspectia-text.component';
import { crudEntitys, typeSize } from 'assets/Typescript-generalities/Types';

@Component({
  selector: 'app-inspectia-icon-text',
  standalone: true,
  imports: [InspectiaTextComponent],
  templateUrl: './inspectia-icon-text.component.html',
  styleUrl: './inspectia-icon-text.component.css'
})
export class InspectiaIconTextComponent{

  @Input() text = "";
  @Input() sizeText: typeSize = "M";
  @Input() backHover = false;
  @Input() color: "red" | "green" | "blue" | "yellow" | "orange" | undefined = undefined;
  @Input() type: crudEntitys = "Analysis";
  @Output() onClick : EventEmitter<crudEntitys> = new EventEmitter<crudEntitys>();

  clickEntity(){
    this.onClick.emit(this.type);
  }

}
