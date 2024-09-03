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
  name: string = '';
  password: string = '';
  msg: string = '';

  constructor(private router: Router, private userService: UserService) {} //for usage in this.router.navigate

  showPassword() {
    const passwordInput = document.getElementById('password') as HTMLInputElement;
    passwordInput.type = 'text';  //so that it is visible
    setTimeout(() => passwordInput.type = 'password', 2000);  //set a timer for the time the password will show
  }

  onLogIn() {
    if (this.name === '' || this.password === '') {
      this.msg = 'Please enter both fields';

      setTimeout(() => this.msg = '', 3000);  //set a timer for the time the error will show
    } else {
      console.log('Name:', this.name);
      console.log('Password:', this.password);
      //logic here
      const authRequest = {
        username: this.name,
        password: this.password
      };


      this.userService.login(authRequest).subscribe(
        (response: string) => {
          // Store JWT token
          localStorage.setItem('token', response);

          // Fetch user profile
          this.userService.getUserProfile(this.name).subscribe(
            (userProfile: string) => {
              console.log('User Profile Response:', userProfile);

              this.router.navigate(['../user-page']); // For now redirect to user-page
              // // Based on profile content, navigate to the appropriate page
              // if (userProfile.includes('Admin')) {
              //   this.router.navigate(['../admin-page']);
              // } else if (userProfile.includes('User')) {
              //   this.router.navigate(['../user-page']);
              // } else {
              //   this.msg = 'Profile content does not match expected values.';
              //   setTimeout(() => this.msg = '', 3000);
              // }
            },
            (error) => {
              console.error('Error fetching user profile', error);
              this.msg = 'Error fetching user profile. Please try again.';
              setTimeout(() => this.msg = '', 3000);
            }
          );
        },
        (error) => {
          console.error('Login error', error);
          this.msg = 'Invalid credentials! Please try again.';
          setTimeout(() => this.msg = '', 3000);  
        }
      );


      //Clear the form fields
      this.name = '';
      this.password = '';

      // //Redirect to the user-page or admin-page (depending on the name given)
      // //logic here.. of which page to open (user or admin)
      // //User-page
      // console.log('Redirect to user-page');
      // this.router.navigate(['../user-page']);
    }
  }

  onSignUp() {
    //Redirect to the sign-up page
    console.log('Redirect to sign-up page');
    this.router.navigate(['../signup-page']); //here we need to change the directory component route as needed
  }
}