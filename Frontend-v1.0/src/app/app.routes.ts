import { Routes } from '@angular/router';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { SignupPageComponent } from './components/signup-page/signup-page.component';
import { UserHomeComponent } from './components/user-page/user-home/user-home.component';
import { AdminHomeComponent } from './components/admin-page/admin-home/admin-home.component';
import { ProfileComponent } from './components/user-page/profile/profile.component';
import { SettingsComponent } from './components/user-page/settings/settings.component';
import { NavbarComponent } from './components/user-page/navbar/navbar.component';

export const routes: Routes = [
  { path: 'welcome-page', component: WelcomePageComponent },
  { path: 'login-page', component: LoginPageComponent },
  { path: 'signup-page', component: SignupPageComponent },

  { path: 'user-page/user-home', component: UserHomeComponent },
  { path: 'admin-page/admin-home', component: AdminHomeComponent },
  { path: 'user-page/navbar', component: NavbarComponent },
  { path: 'user-page/profile', component: ProfileComponent },
  { path: 'user-page/settings', component: SettingsComponent },

  
  { path: 'user-page', redirectTo: '/user-page/user-home' },
  { path: 'admin-page', redirectTo: '/admin-page/admin-home' },

  { path: '', redirectTo: '/welcome-page', pathMatch: 'full' }, //default route
  { path: '**', redirectTo: '/welcome-page' } //handle undefined routes
];