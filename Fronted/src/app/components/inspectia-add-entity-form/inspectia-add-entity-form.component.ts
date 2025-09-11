import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InspectiaButtonComponent } from '@components/inspectia-button/inspectia-button.component';
import { InspectiaInputFormComponent } from '@components/inspectia-input-form/inspectia-input-form.component';
import { InspectiaTextComponent } from '@components/inspectia-text/inspectia-text.component';
import { CloseIconComponent } from '@icons/close-icon/close-icon.component';
import { statesDefect } from 'assets/Defaults/Default-values';
import { crudEntitys, state } from 'assets/Typescript-generalities/Types';

@Component({
  selector: 'app-inspectia-add-entity-form',
  standalone: true,
  imports: [
    InspectiaTextComponent,
    CloseIconComponent,
    InspectiaInputFormComponent,
    InspectiaButtonComponent,
  FormsModule],
  templateUrl: './inspectia-add-entity-form.component.html',
  styleUrl: './inspectia-add-entity-form.component.css'
})
export class InspectiaAddEntityFormComponent implements OnChanges {
  @Input() formTitle: string = 'Generar análisis';
  @Input() open: boolean = true;
  optionsState: state[] = statesDefect;
  @Input() typeEntityActual = "Entidad";
  @Input() typeEntity: crudEntitys = "Analysis";
  @Output() closedForm: EventEmitter<Event> = new EventEmitter();
  @Output() submitFormEmitter: EventEmitter<Event> = new EventEmitter();
  @Input() operation = "none";
  loadedFiles: FileList | null = null;
    @ViewChild('formInput') formInput!: InspectiaInputFormComponent;


  submitForm(e: Event) {
    e.preventDefault()
    this.submitFormEmitter.emit(e);

  }

  closeForm(e: Event) {
    this.open = false;
    this.closedForm.emit(e);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['operation']) {
      switch (this.operation) {
        case ('generateAnalysis'):
          this.formTitle = 'Generar análisis';
          break;
      }
    }
  }

  clearFilesInput(){
    this.formInput.clearInput();
  }
}
