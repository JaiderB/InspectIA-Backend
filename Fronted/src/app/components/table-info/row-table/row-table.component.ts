import { Component, HostBinding, Input, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-row-table',
  standalone: true,
  imports: [],
  templateUrl: './row-table.component.html',
  styleUrl: './row-table.component.css'
})
export class RowTableComponent {

  @Input() header: boolean = false;
  public renderer: Renderer2;
  @Input() indentationLevel: number = 0;
   @Input() backgroundClass: string = '';


  constructor(private rederer: Renderer2) {
    this.renderer = rederer;
  }

  getRenderer(): Renderer2 {
    return this.renderer;
  }

  @HostBinding('class')
  get hostClasses(): string {
    return this.backgroundClass;
  }
}
