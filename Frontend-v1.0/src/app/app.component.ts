import { Component } from '@angular/core';
import { RouterOutlet, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  title = 'Frontend-v1.0';
  email: string = '';
  password: string = '';
  msg: string = '';

  constructor(private router: Router) {} //for usage in this.router.navigate

  showPassword() {
    const passwordInput = document.getElementById('password') as HTMLInputElement;
    passwordInput.type = 'text';  //so that it is visible
    setTimeout(() => passwordInput.type = 'password', 2000);  //set a timer for the time the password will show
  }

  onLogIn() {
    if (this.email === '' || this.password === '') {
      this.msg = 'Please enter both fields';

      setTimeout(() => this.msg = '', 3000);  //set a timer for the time the error will show
    } else {
      console.log('Email:', this.email);
      console.log('Password:', this.password);
      //logic here

      //Clear the form fields
      this.email = '';
      this.password = '';
    }

    //Redirect to the user-page or admin-page (depending on the email given)
    //logic here.. of which page to open (user or admin)
    
    //User-page
    console.log('Redirect to user-page');
    this.router.navigate(['/user-page']);
  }

  onSignUp() {
    //Redirect to the sign-up page
    console.log('Redirect to sign-up page');
    this.router.navigate(['/...']); //here we need to change the directory component route as needed
  }
}