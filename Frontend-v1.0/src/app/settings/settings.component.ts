import { Component } from '@angular/core';
import { RouterOutlet, Router, RouterLink, RouterLinkActive} from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [RouterOutlet, CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent {

  constructor(private router: Router) {} //for usage in this.router.navigate
}
