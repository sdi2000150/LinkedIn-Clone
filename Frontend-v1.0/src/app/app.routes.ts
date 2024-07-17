import { Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { UserPageComponent } from './user-page/user-page.component';
import { SigningComponent } from './signing/signing.component';

export const routes: Routes = [
  { path: 'login-page', component: LoginPageComponent },
  { path: 'user-page', component: UserPageComponent },
  { path: 'signing', component: SigningComponent },

  { path: '', redirectTo: '/login-page', pathMatch: 'full' }, //default route
  { path: '**', redirectTo: '/login-page' } //handle undefined routes
];