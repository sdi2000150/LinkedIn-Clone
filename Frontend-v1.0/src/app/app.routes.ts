import { Routes } from '@angular/router';

// Import all the components that will be used in the routes
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { SignupPageComponent } from './components/signup-page/signup-page.component';
import { UserHomeComponent } from './components/user-page/user-home/user-home.component';
import { AdminHomeComponent } from './components/admin-page/admin-home/admin-home.component';
import { ProfileComponent } from './components/user-page/profile/profile.component';
import { SettingsComponent } from './components/user-page/settings/settings.component';
import { NavbarComponent } from './components/user-page/navbar/navbar.component';
import { JobsComponent } from './components/user-page/jobs/jobs.component';
import { UsersListComponent } from './components/admin-page/users-list/users-list.component';
import { NetworkComponent } from './components/user-page/network/network.component';
import { NotificationsComponent } from './components/user-page/notifications/notifications.component';
import { MessagingComponent } from './components/user-page/messaging/messaging.component';
import { ProfileViewComponent } from './components/user-page/profile-view/profile-view.component';
import { MyContactsPostsComponent } from './components/user-page/my-contacts-posts/my-contacts-posts.component';
import { MyPostsComponent } from './components/user-page/my-posts/my-posts.component';
import { UserViewComponent } from './components/admin-page/user-view/user-view.component';

// Import the guard (to protect specific routes)
import { authGuard } from './auth.guard';

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
  { path: 'user-page/network', component: NetworkComponent, canActivate: [authGuard] },
  { path: 'user-page/notifications', component: NotificationsComponent, canActivate: [authGuard] },
  { path: 'user-page/messaging', component: MessagingComponent, canActivate: [authGuard] },
  { path: 'user-page/profile-view/:email', component: ProfileViewComponent, canActivate: [authGuard] },
  { path: 'user-page/my-contacts-posts', component: MyContactsPostsComponent, canActivate: [authGuard] },
  { path: 'user-page/my-posts', component: MyPostsComponent, canActivate: [authGuard] },

  { path: 'admin-page/users-list', component: UsersListComponent, canActivate: [authGuard] },
  { path: 'admin-page/user-view/:email', component: UserViewComponent, canActivate: [authGuard] },

  
  { path: 'user-page', redirectTo: '/user-page/user-home' },
  { path: 'admin-page', redirectTo: '/admin-page/admin-home' },

  { path: '', redirectTo: '/welcome-page', pathMatch: 'full' }, //default route
  { path: '**', redirectTo: '/welcome-page' } //handle undefined routes
];