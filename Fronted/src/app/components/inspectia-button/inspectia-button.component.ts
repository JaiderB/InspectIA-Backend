import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-inspectia-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inspectia-button.component.html',
  styleUrl: './inspectia-button.component.css'
})
export class InspectiaButtonComponent implements OnInit {

  @Input() buttonText: string = 'Button';
  @Input() buttonStyle: "minimalist" | "basic" = "basic";
  @Input() color: string = 'default';
  @Input() buttonType: "button-icon" | "default" = 'default';
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
  @Input() form: string | null = null;
  @Output() onPress: EventEmitter<Event> = new EventEmitter<Event>();
  public classesButton: string = "";

  onPressEvent(event: Event): void {
    this.onPress.emit(event);
  }

  ngOnInit(): void {
    this.classesButton = `${this.color} ${this.buttonStyle}`;
  }
}
