import { Routes } from '@angular/router';
import { LoginPageComponent } from '@pages/login-page/login-page.component';
import { PrincipalPageComponent } from '@pages/principal-page/principal-page.component';

export const routes: Routes = [
  {path: '', component: LoginPageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'principal', component: PrincipalPageComponent}
];
