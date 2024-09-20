import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Import CommonModule (for NgFor... usage on the HTML)
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-posts',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './my-posts.component.html',
  styleUrl: './my-posts.component.css'
})
export class MyPostsComponent implements OnInit {
  articles: any[] = []; // Store articles of the user

  token: string | null = null; // Store token from localStorage

  constructor(private userService: UserService, private router: Router) {} //Inject the UserService

  ngOnInit(): void {
    // Fetch the token from localStorage
    this.token = localStorage.getItem('token');

    // Check if token is available
    if (this.token) {
      //logic here...


    } else {
      // If no token found, redirect to login page
      this.router.navigate(['../../login-page']);
    }
  }
}
