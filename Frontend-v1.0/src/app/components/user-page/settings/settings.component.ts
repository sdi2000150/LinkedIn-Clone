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
  oldPassword: string = '';

  token: string = localStorage.getItem('token') || '';

  msgError: string = '';
  msgSuccess: string = '';

  constructor(private userService: UserService, private router: Router) {}

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

  changeEmailPassword() {
    // Old password MUST be provided, then email or password or both can be changed
    if (this.oldPassword && (this.email || (this.password && (this.password === this.passwordConf)))) {
      this.userService.updateEmailPassword(this.token, this.oldPassword, this.email, this.password).subscribe(response => {
        if (response) {
          this.msgSuccess = 'Email and/or Password updated successfully';
          setTimeout(() => this.msgSuccess = '', 3000);  // Clear the message after 3 seconds
        } else {
          this.msgError = 'Failed to update Email and/or Password';
          setTimeout(() => this.msgError = '', 3000);  // Clear the message after 3 seconds
        }
      }, error => {
        this.msgError = 'Failed to update Email and/or Password';
        setTimeout(() => this.msgError = '', 3000);  // Clear the message after 3 seconds
      });
    } else {
      this.msgError = 'Please fill in the required fields';
      setTimeout(() => this.msgError = '', 3000);  // Clear the message after 3 seconds
    }
    this.oldPassword = '';
    this.email = '';
    this.password = '';
    this.passwordConf = '';
  }

}
