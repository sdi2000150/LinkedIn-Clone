import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Import CommonModule (for NgFor... usage on the HTML)
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-contacts-posts',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './my-contacts-posts.component.html',
  styleUrl: './my-contacts-posts.component.css'
})
export class MyContactsPostsComponent implements OnInit {
  articles: any[] = []; // Store articles of the user

  token: string | null = null; // Store token from localStorage

  constructor(private userService: UserService, private router: Router) {} //Inject the UserService

  ngOnInit(): void {
    // Fetch the token from localStorage
    this.token = localStorage.getItem('token');

    // Check if token is available
    if (this.token) {
      // Fetch contact articles using the token
      this.userService.getContactArticles(this.token).subscribe(
        (data) => {
          this.articles = data;
          console.log('Contact articles fetched successfully', this.articles);
        },
        (error) => {
          console.error('Error fetching contact articles', error);
        }
      );
    } else {
      // If no token found, redirect to login page
      this.router.navigate(['../../login-page']);
    }
  }
}
