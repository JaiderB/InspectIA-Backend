import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-inspectia-logo-container',
  standalone: true,
  imports: [],
  templateUrl: './inspectia-logo-container.component.html',
  styleUrl: './inspectia-logo-container.component.css'
})
export class InspectiaLogoContainerComponent {

  @Input() urlSource = "";
  @Input() contentType : 'logo' | 'icon' | 'default'  = "default";
  @Input() width: number = 100;
  @Input() height: number = 400;

}
