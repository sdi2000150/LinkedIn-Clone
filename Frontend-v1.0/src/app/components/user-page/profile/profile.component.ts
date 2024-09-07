import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  userName: string = 'Empty Name'; // Placeholder for the user's name

  constructor() {}

  ngOnInit(): void {
    // Placeholder for future logic to load profile data from backend
  }
}
