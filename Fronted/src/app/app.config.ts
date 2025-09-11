import { provideHttpClient } from '@angular/common/http';
import { ApplicationConfig } from '@angular/core';
import { provideClientHydration } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { routes } from './app.routes';


export const appConfig: ApplicationConfig = {
  providers: [
    CookieService,
    provideRouter(routes), provideClientHydration(),
    provideHttpClient()
  ]
};
