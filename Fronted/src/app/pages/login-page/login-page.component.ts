import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { BasicModalComponent } from '@app/components/Modals/basic-modal/basic-modal.component';
import { LoginRequest } from '@app/core/models/Auth/login-request.model';
import { AuthService } from '@app/core/services/auth.service';
import { InspectiaButtonComponent } from '@components/inspectia-button/inspectia-button.component';
import { InspectiaInputFormComponent } from '@components/inspectia-input-form/inspectia-input-form.component';
import { InspectiaLogoContainerComponent } from '@components/inspectia-logo-container/inspectia-logo-container.component';
import { InspectiaTextComponent } from '@components/inspectia-text/inspectia-text.component';
import { modalTypes } from 'assets/Typescript-generalities/Types';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [
    InspectiaLogoContainerComponent,
    InspectiaTextComponent,
    InspectiaInputFormComponent,
    InspectiaButtonComponent,
    FormsModule,
    BasicModalComponent
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {

  email: string = '';
  password: string = '';
  typeModal: modalTypes = 'info'
  showModal = false
  currentMessageModal: modalTypes = "info"

  constructor(
    private router: Router,
    private authService: AuthService
  ) { };


  showUnableOptionModal() {
    this.currentMessageModal = "unableOption"
    this.typeModal = "info"
    this.showModal = true
  }

  showRegister(event: Event) {
    this.showUnableOptionModal();
  }

  showRestore(event: Event) {
    this.showUnableOptionModal();
  }

  login() {

    if (this.email != null && this.password != null && this.email != "" && this.password != "") {
      const credentials: LoginRequest = {
        email: this.email,
        password: this.password
      };

      this.authService.login(credentials).subscribe({
        next: (response) => {
          this.router.navigate([`/principal`]);
        },
        error: (err) => {
          if (err.status == 401 || err.status == 403) {
            this.currentMessageModal = "wrongCredentials";
          } else {
            this.currentMessageModal = "error";
          }

          this.typeModal = "error"
          this.showModal = true

        }
      })
    } else {
      this.currentMessageModal = "emptyFields";
      this.typeModal = "error";
      this.showModal = true;
    }
  }

  closeModal() {
    this.showModal = false;
  }
}

