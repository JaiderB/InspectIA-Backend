import { Component, Input, OnInit } from '@angular/core';
import { typeSize } from 'assets/Typescript-generalities/Types';

@Component({
  selector: 'app-inspectia-text',
  standalone: true,
  imports: [],
  template: `


      @switch (typeText) {

      @case ('title'){
        <h1 class="title {{generalClasses}}">{{text}}</h1>
      }
      @case ('subtitle'){
        <h2 class="subtitle {{generalClasses}}">{{text}}</h2>
      }
      @case ('subtitle-min'){
        <h3 class="subtitle-min {{generalClasses}}">{{text}}</h3>
      }
      @case ('link'){
        <a class="link {{generalClasses}}">{{text}}</a>
      }
      @case('label'){
        <label class="label {{generalClasses}} {{labelLink? 'label-link' : ''}} " for="{{labelFor}}" >{{text}}</label>
      }
      @case ('title-subtitle') {
        <div class="container-tt">
          <h1 class="title {{generalClasses}}">{{text}}</h1>
          <h2 class="subtitle XS title-subtitle">{{subText}}</h2>
        </div>

      }
      @default {
        <span class="{{typeText}} {{generalClasses}}">{{text}}</span>
      }

    }
  `,

  styleUrl: './inspectia-text.component.css'
})
export class InspectiaTextComponent implements OnInit{

  @Input() typeText: 'title' | 'subtitle' | 'subtitle-min' | 'description' | 'link' | 'label' | 'title-subtitle';
  @Input() size: typeSize | 'DEFAULT';
  @Input() text: string;
  @Input() labelFor = "";
  @Input() labelLink: Boolean = false;
  @Input() fontStyle: "default" | "black" = "default";
  @Input() chain :boolean = false;
  @Input() subText = "Sub Text";
  generalClasses:string = "";

  constructor(){
    this.typeText = 'description';
    this.size = "DEFAULT";
    this.text = "";
  }

  ngOnInit(): void {
    this.generalClasses =  `${this.size} ${this.fontStyle} ${this.chain? "chain" : ""}`;
  }

}
