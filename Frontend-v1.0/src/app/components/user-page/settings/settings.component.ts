import { Component } from '@angular/core';
import { RouterOutlet, Router, RouterLink, RouterLinkActive} from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [NavbarComponent, RouterOutlet, CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent {
  email: string = '';
  password: string = '';
  passwordConf: string = '';
  token: string = localStorage.getItem('token') || '';

  constructor(private userService: UserService, private router: Router) {}

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

    //TODO
  changeEmail() {
    if (this.email) {
      this.userService.updateEmail(this.email, this.token).subscribe(response => {
        alert('Email updated successfully');
      }, error => {
        alert('Failed to update email');
      });
    } else {
      alert('Please enter a new email address');
    }
  }
  //TODO
  changePassword() {
    if (this.password && this.password === this.passwordConf) {
      this.userService.updatePassword(this.password, this.token).subscribe(response => {
        alert('Password updated successfully');
      }, error => {
        alert('Failed to update password');
      });
    } else {
      alert('Passwords do not match');
    }
  }
}
