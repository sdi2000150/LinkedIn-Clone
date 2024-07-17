import { Component } from '@angular/core';

@Component({
  selector: 'app-signing',
  standalone: true,
  imports: [],
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
  
  msg: string = '';


  showPassword() {
    const passwordInput = document.getElementById('password') as HTMLInputElement;
    passwordInput.type = 'text';  //so that it is visible
    setTimeout(() => passwordInput.type = 'password', 2000);  //set a timer for the time the password will show
  }


  SignUp() {
    //Redirect to the sign-up page
    console.log("On sign up is called")
    if (this.email === '' || this.password === '' || this.name === '' || this.surname === '' || this.passwordConf === '') {
      this.msg = 'Please enter all fields';

      setTimeout(() => this.msg = '', 3000);  //set a timer for the time the error will show
    } else {
      //console.log('Email:', this.email);
      //console.log('Password:', this.password);
      //logic here

      //Clear the form fields

      this.email = '';
      this.password = '';
      this.name ='';
      this.password = '';
      this.passwordConf = '';

      //Redirect to the user-page or admin-page (depending on the email given)
      //logic here.. of which page to open (user or admin)
      //User-page
      console.log('Redirect to user-page');
    }



    console.log('Sign-up User');
  }
}
