import { Routes } from '@angular/router';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { SignupPageComponent } from './components/signup-page/signup-page.component';
import { UserHomeComponent } from './components/user-page/user-home/user-home.component';
import { AdminHomeComponent } from './components/admin-page/admin-home/admin-home.component';
import { ProfileComponent } from './components/user-page/profile/profile.component';
import { SettingsComponent } from './components/user-page/settings/settings.component';
import { NavbarComponent } from './components/user-page/navbar/navbar.component';
import { JobsComponent } from './components/user-page/jobs/jobs.component';

import { authGuard } from './auth.guard';  // Import your guard

export const routes: Routes = [
  { path: 'welcome-page', component: WelcomePageComponent },
  { path: 'login-page', component: LoginPageComponent },
  { path: 'signup-page', component: SignupPageComponent },

  { path: 'user-page/user-home', component: UserHomeComponent, canActivate: [authGuard] },
  { path: 'admin-page/admin-home', component: AdminHomeComponent, canActivate: [authGuard] },
  { path: 'user-page/navbar', component: NavbarComponent, canActivate: [authGuard] },
  { path: 'user-page/profile', component: ProfileComponent, canActivate: [authGuard] },
  { path: 'user-page/settings', component: SettingsComponent, canActivate: [authGuard] },
  { path: 'user-page/jobs', component: JobsComponent, canActivate: [authGuard] },

  
  { path: 'user-page', redirectTo: '/user-page/user-home' },
  { path: 'admin-page', redirectTo: '/admin-page/admin-home' },

  { path: '', redirectTo: '/welcome-page', pathMatch: 'full' }, //default route
  { path: '**', redirectTo: '/welcome-page' } //handle undefined routes
];