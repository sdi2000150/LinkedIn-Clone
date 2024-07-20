import { Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { UserPageComponent } from './user-page/user-page.component';
import { SigningComponent } from './signing/signing.component';
import { ProfileComponent } from './profile/profile.component';
import { SettingsComponent } from './settings/settings.component';
import { NavbarComponent } from './navbar/navbar.component';

export const routes: Routes = [
  { path: 'login-page', component: LoginPageComponent },
  { path: 'signing', component: SigningComponent },

  { path: 'navbar', component: NavbarComponent },
  { path: 'user-page', component: UserPageComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'settings', component: SettingsComponent },


  { path: '', redirectTo: '/login-page', pathMatch: 'full' }, //default route
  { path: '**', redirectTo: '/login-page' } //handle undefined routes
];