import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InspectiaTextComponent } from '@components/inspectia-text/inspectia-text.component';
import { state } from 'assets/Typescript-generalities/Types';


type inputType = 'password' | 'text' | 'select' | 'email' | 'number' | 'files';

@Component({
  selector: 'app-inspectia-input-form',
  standalone: true,
  imports: [InspectiaTextComponent, FormsModule],
  templateUrl: './inspectia-input-form.component.html',
  styleUrl: './inspectia-input-form.component.css'
})
export class InspectiaInputFormComponent implements OnInit {

  @Input() placeholder = "";
  @Input() isValidInput = false;
  @Input() label = "Label";
  @Input() required = false;
  @Input() type: inputType = "text";
  @Output() valueChange: EventEmitter<string> = new EventEmitter();
  checked = false;
  @Input() value: string = '';
  @Input() files: FileList | null = null;
  idInput: string = "";
  @Input() optionsSelect: state[] = [];
  @ViewChild('realFile') fileInput!: ElementRef<HTMLInputElement>;
  loadedFiles: FileList | null = null;
  @ViewChild('filesInput') filesInput!: ElementRef;


  togglePassword() {
    this.checked = !this.checked;
  }

  onFilesSelected(e: Event) {
    const input = e.target as HTMLInputElement;

    this.loadedFiles = input.files;
  }

  constructor() { }

  clickRealInput() {
    this.fileInput.nativeElement.click();
  }

  generateIdInput(): string {
    return "input-" + crypto.randomUUID();
  }

  ngOnInit(): void {
    this.optionsSelect = [{ id: 0, name: "Seleccione" }, ...this.optionsSelect];
    this.idInput = this.generateIdInput();
  }


  onInputChange(newValue: string) {
    this.value = newValue;
    this.valueChange.emit(this.value);
  }

  public clearInput(): void {

    if (this.filesInput) {
      this.filesInput.nativeElement.value = '';
      this.filesInput.nativeElement.files = null;
    }

    this.loadedFiles = null;
  }

}
