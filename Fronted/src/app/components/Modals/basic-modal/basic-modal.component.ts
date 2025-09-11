import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { DeleteModalIconComponent } from '@app/components/Icons/delete-modal-icon/delete-modal-icon.component';
import { SeeIconComponent } from '@app/components/Icons/edit-icon/see-icon.component';
import { InspectiaButtonComponent } from '@components/inspectia-button/inspectia-button.component';
import { InspectiaTextComponent } from '@components/inspectia-text/inspectia-text.component';
import { ErrorIconComponent } from '@icons/error-icon/error-icon.component';
import { SuccessIconComponent } from '@icons/success-icon/success-icon.component';
import { WarningIconComponent } from '@icons/warning-icon/warning-icon.component';
import { messagesDescription, messagesTitle } from 'assets/Defaults/Default-values';
import { color, modalTypes } from 'assets/Typescript-generalities/Types';


@Component({
  selector: 'app-basic-modal',
  standalone: true,
  imports: [SeeIconComponent,
    InspectiaTextComponent,
    InspectiaButtonComponent,
    SuccessIconComponent,
    ErrorIconComponent,
    WarningIconComponent,
    DeleteModalIconComponent
  ],
  templateUrl: './basic-modal.component.html',
  styleUrl: './basic-modal.component.css'
})
export class BasicModalComponent implements OnChanges {

  titleModal = "Modal";
  @Input() descriptionModal = "Modal description";
  @Input() colorPrincipal: color = 'default';
  @Input() typeModal: modalTypes = 'info';
  @Input() opened: boolean = false;
  @Output() closedModal: EventEmitter<Event> = new EventEmitter<Event>();
  @Output() aceptButtonClicked: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input() defaultMessages = true;
  @Input() customMessageType: modalTypes = 'info';

  buttonPrincipalText = "";

  continue() {
    this.aceptButtonClicked.emit(true);
  }

  closeModal(e: Event) {
    this.opened = false;
    this.closedModal.emit()
  }

  openModal() {
    this.opened = true;
  }

  setDefaultInfo(): void {
    this.titleModal = this.defaultMessages ? messagesTitle[this.typeModal] : messagesTitle[this.customMessageType];
    this.descriptionModal = this.defaultMessages ? messagesDescription[this.typeModal] : messagesDescription[this.customMessageType];
  }

  ngOnChanges(changes: SimpleChanges): void {

    if (changes['opened']) {
      console.log('Opened ha cambiado a:', this.opened);
    }

    if (this.typeModal === 'success' || this.typeModal === 'info' || this.typeModal === 'error') {
      this.buttonPrincipalText = 'Aceptar';
    } else {
      this.buttonPrincipalText = 'Cancelar';
    }


    this.setDefaultInfo();


  }

}
