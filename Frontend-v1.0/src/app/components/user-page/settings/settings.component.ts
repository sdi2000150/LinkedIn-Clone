import { Component } from '@angular/core';
import { RouterOutlet, Router, RouterLink, RouterLinkActive} from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [NavbarComponent, RouterOutlet, CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent {
  password: string = '';
  passwordConf: string = '';
  
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

}
