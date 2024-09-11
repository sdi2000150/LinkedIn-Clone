import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { UserService } from './services/user-service/user.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export const authGuard: CanActivateFn = (route, state): Observable<boolean> | Promise<boolean> | boolean => {
  const router = inject(Router);
  const userService = inject(UserService);
  const token = localStorage.getItem('token');

  if (token) {
    // If token exists, check if it is expired
    if (userService.isTokenExpired(token)) {
      // If token is expired, redirect to login
      router.navigate(['/login']);
      return false;
    }

    // If token is not expired, check if the user is admin or not
    return userService.isUserAdmin(token).pipe(
      map((isAdmin: boolean) => {
        if (isAdmin && state.url.startsWith('/admin-page')) {
          // Allow admin access to admin pages
          return true;
        } else if (!isAdmin && state.url.startsWith('/user-page')) {
          // Allow user access to user pages
          return true;
        } else {
          // If role mismatch, redirect to login or appropriate page
          if (isAdmin) {
            router.navigate(['/admin-page']); // redirect admin to admin page
          } else {
            router.navigate(['/user-page']); // redirect user to user page
          }
          return false; // Block access
        }
      })
    );
  } else {
    // Token doesn't exist, redirect to login
    router.navigate(['/login']);
    // return false;
    return true; // For now, allow access
  }
};