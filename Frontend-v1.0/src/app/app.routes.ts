import { Routes } from '@angular/router';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { UserPageComponent } from './components/user-page/user-page.component';
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { SignupPageComponent } from './components/signup-page/signup-page.component';
import { ProfileComponent } from './components/profile/profile.component';
import { SettingsComponent } from './components/settings/settings.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';

export const routes: Routes = [
  { path: 'welcome-page', component: WelcomePageComponent },
  { path: 'login-page', component: LoginPageComponent },
  { path: 'signup-page', component: SignupPageComponent },

  { path: 'user-page', component: UserPageComponent },
  { path: 'admin-page', component: AdminPageComponent },
  { path: 'navbar', component: NavbarComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'settings', component: SettingsComponent },


  { path: '', redirectTo: '/welcome-page', pathMatch: 'full' }, //default route
  { path: '**', redirectTo: '/welcome-page' } //handle undefined routes
];