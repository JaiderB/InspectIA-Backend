import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { routes } from '@app/app.routes';
import { LoginPageComponent } from '@pages/login-page/login-page.component';
import { PrincipalPageComponent } from '@pages/principal-page/principal-page.component';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    PrincipalPageComponent,
    LoginPageComponent,
    RouterModule.forRoot(routes, {useHash: true}),
  ]
})
export class InspectiaAppModule { }
