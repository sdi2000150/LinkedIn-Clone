import { Routes } from '@angular/router';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { UserPageComponent } from './components/user-page/user-page.component';
import { SigningComponent } from './components/signing/signing.component';
import { ProfileComponent } from './components/profile/profile.component';
import { SettingsComponent } from './components/settings/settings.component';
import { NavbarComponent } from './components/navbar/navbar.component';

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