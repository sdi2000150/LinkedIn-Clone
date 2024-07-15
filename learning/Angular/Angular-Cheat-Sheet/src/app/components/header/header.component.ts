import { Component } from '@angular/core';
import { ButtonComponent } from '../button/button.component'; // Import the ButtonComponent

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  //component logic here
  title = 'Angular-Cheat-Sheet';

  toggleAddTask() {
    console.log('toggle');
  }
}
