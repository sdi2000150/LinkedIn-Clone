import { Component } from '@angular/core';
import { RouterOutlet, Router, RouterLink, RouterLinkActive} from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user-service/user.service';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [RouterOutlet, CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})

export class LoginPageComponent {
  title = 'Frontend-v1.0';
  email: string = '';
  password: string = '';
  msg: string = '';

  constructor(private router: Router, private userService: UserService) {} // For usage in this.router.navigate

  showPassword() {
    const passwordInput = document.getElementById('password') as HTMLInputElement;
    passwordInput.type = 'text';  // So that it is visible
    setTimeout(() => passwordInput.type = 'password', 2000);  // Set a timer for the time the password will show
  }

  onLogIn() {
    if (this.email === '' || this.password === '') {
      this.msg = 'Please enter both fields';
      setTimeout(() => this.msg = '', 3000);  // Set a timer for the time the error will show

    } else {
      console.log('Email:', this.email);
      console.log('Password:', this.password);
      const authRequest = {
        email: this.email,
        password: this.password
      };
      // Call the login service
      this.userService.login(authRequest).subscribe(
        (response: string) => {
          // Store JWT token
          localStorage.setItem('token', response);

          // Fetch user profile
          const token = localStorage.getItem('token'); // Fetch the token from localStorage
          if (token) {
            this.userService.getUserProfileFromToken(token).subscribe(
              (data: any) => {
                if (data.role === 'ROLE_USER') {
                  // Redirect to the user-page
                  console.log('Redirect to user-page');
                  this.router.navigate(['../user-page']);

                } else if (data.role === 'ROLE_ADMIN') {
                  // Redirect to the admin-page
                  console.log('Redirect to admin-page');
                  this.router.navigate(['../admin-page']);
                }
              },
              (error) => {
                console.error('Error fetching user data', error);
              }
            );
          } else {
            console.error('No token found');
          }
        },
        (error) => {
          console.error('Login error', error);
          this.msg = 'Invalid credentials! Please try again.';
          setTimeout(() => this.msg = '', 3000);  
        }
      );

      //Clear the form fields
      this.email = '';
      this.password = '';
    }
  }

  onSignUp() {
    // Redirect to the sign-up page
    console.log('Redirect to sign-up page');
    this.router.navigate(['../signup-page']);
  }
}