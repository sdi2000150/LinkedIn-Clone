import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (token) {
    // Token exists, allow access
    return true;
  } else {
    // Token doesn't exist, redirect to login
    router.navigate(['/login']);
    return false;
    // return true; // For now, allow access
  }
};