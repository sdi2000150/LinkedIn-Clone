import { Component } from '@angular/core';
import { RouterOutlet, Router, RouterLink, RouterLinkActive} from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-welcome-page',
  standalone: true,
  imports: [RouterOutlet, CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './welcome-page.component.html',
  styleUrl: './welcome-page.component.css'
})
export class WelcomePageComponent {

  constructor(private router: Router) {} // for usage in this.router.navigate

  onLogIn() {
    // Redirect to the login page
    console.log('Redirect to login page');
    this.router.navigate(['../login-page']);
  }

  onSignUp() {
    // Redirect to the sign-up page
    console.log('Redirect to sign-up page');
    this.router.navigate(['../signup-page']);
  }
}
