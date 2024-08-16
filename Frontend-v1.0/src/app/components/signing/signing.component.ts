import { Component } from '@angular/core';
import { RouterOutlet, Router, RouterLink, RouterLinkActive} from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signing',
  standalone: true,
  imports: [RouterOutlet, CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './signing.component.html',
  styleUrl: './signing.component.css'
})
export class SigningComponent {
  title = 'Frontend-v1.0';
  email: string = '';
  password: string = '';
  passwordConf: string = '';
  name: string = '';
  surname: string = '';
  phone: string = '';
  
  msg: string = '';

  constructor(private router: Router) {} //for usage in this.router.navigate


  showPassword() {
    const passwordInput = document.getElementById('password') as HTMLInputElement;
    passwordInput.type = 'text';  //so that it is visible
    setTimeout(() => passwordInput.type = 'password', 2000);  //set a timer for the time the password will show
  }
  showPasswordConf() {
    const passwordInput = document.getElementById('passwordConf') as HTMLInputElement;
    passwordInput.type = 'text';  //so that it is visible
    setTimeout(() => passwordInput.type = 'password', 2000);  //set a timer for the time the password will show
  }

  SignUp() {
    //Redirect to the sign-up page
    console.log("On sign up is called")
    if (this.email === '' || this.password === '' || this.name === '' || this.surname === '' || this.passwordConf === '' || this.phone === '') {
      this.msg = 'Please enter all fields';

      setTimeout(() => this.msg = '', 3000);  //set a timer for the time the error will show
    } else {
      //logic here

      //Clear the form fields
      this.email = '';
      this.password = '';
      this.name ='';
      this.surname = '';
      this.password = '';
      this.passwordConf = '';
      this.phone = '';

      //For now it redirects to login page
      console.log('Redirect to login-page');
      this.router.navigate(['../login-page']);
    }

    console.log('Sign-up User');
  }
}
