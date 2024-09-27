import { Component } from '@angular/core';
import { RouterOutlet, Router, RouterLink, RouterLinkActive} from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user-service/user.service';


@Component({
  selector: 'app-signup-page',
  standalone: true,
  imports: [RouterOutlet, CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './signup-page.component.html',
  styleUrl: './signup-page.component.css'
})
export class SignupPageComponent {
  title = 'Frontend-v1.0';
  email: string = '';
  password: string = '';
  passwordConf: string = '';
  name: string = '';
  lastname: string = '';
  username: string = '';
  phone: string = '';
  birthdate: string = '';
  role: string = 'ROLE_USER';  // Default to "User"
  
  msgError: string = '';
  msgSuccess: string = '';

  constructor(private router: Router, private userService: UserService) {} // For usage in this.router.navigate


  showPassword() {
    const passwordInput = document.getElementById('password') as HTMLInputElement;
    passwordInput.type = 'text';  // So that it is visible
    setTimeout(() => passwordInput.type = 'password', 2000);  // Set a timer for the time the password will show
  }
  showPasswordConf() {
    const passwordInput = document.getElementById('passwordConf') as HTMLInputElement;
    passwordInput.type = 'text';  // So that it is visible
    setTimeout(() => passwordInput.type = 'password', 2000);  // Set a timer for the time the password will show
  }

  SignUp() {
    //Redirect to the sign-up page
    console.log("On sign up is called")
    if (this.email === '' || this.password === '' || this.username === '' || this.name === '' || this.lastname === '' || this.passwordConf === '') {
      this.msgError = 'Please enter all the required fields';
      setTimeout(() => this.msgError = '', 3000);  // Set a timer for the time the error will show

    } else if (this.password !== this.passwordConf) {
      this.msgError = 'Passwords do not match';
      setTimeout(() => this.msgError = '', 3000);  // Clear the message after 3 seconds
      
    } else {
      const user = {
        name: this.name,
        lastname: this.lastname,
        username: this.username,
        phone: this.phone,
        email: this.email,
        password: this.password,
        birthdate: this.birthdate,
        role: 'ROLE_USER'  // Use the default role
      };

      // Use UserService to send a signup request
      this.userService.signup(user).subscribe(
        (response: boolean) => {
          if (response) {
            console.log('User added successfully', response);
            this.msgSuccess = 'Signup successful! Redirecting to login...';
            setTimeout(() => {
              this.msgError = '';
              this.router.navigate(['../login-page']);  // Redirect to login page
            }, 2000);
          } else {
            console.error('Error adding user: email already in use', response);
            this.msgError = 'Signup failed! Please try again with another email.';
            setTimeout(() => this.msgError = '', 3000);  // Clear the message after 3 seconds
          }
        },
        (error) => {
          console.error('Error adding user', error);
          
          this.msgError = 'Signup failed! Please try again.';
          setTimeout(() => this.msgError = '', 3000);  // Clear the message after 3 seconds
        }
      );

      // Clear the form fields
      this.email = '';
      this.password = '';
      this.name ='';
      this.username = '';
      this.lastname = '';
      this.password = '';
      this.passwordConf = '';
      this.phone = '';
      this.birthdate = '';
      this.role = 'ROLE_USER';  // Reset to default role
    }
  }
}
