import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { typeSize } from 'assets/Typescript-generalities/Types';

@Component({
  selector: 'icon',
  standalone: true,
  imports: [],
  template: '',
  styles: ''
})
export class Icon implements OnInit{

  @Input() hover:boolean = false;
  @Input() size: typeSize = "M";
  @Input() chain: boolean = false;
  @Output() onClick: EventEmitter<Event> = new EventEmitter<Event>()
  classesSvg: string = '';

  onClickSvg(event: Event): void{
    console.log("click");
    this.onClick.emit(event);
  }

  ngOnInit(): void {
    this.classesSvg = `${this.hover? 'active': ''} ${this.size} ${this.chain? 'chain': ''}`
  }

}
