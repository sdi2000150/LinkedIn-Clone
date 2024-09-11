import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Import CommonModule (for NgFor... usage on the HTML)
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-home',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  ArticleText: string = 'Empty'; //Placeholder

  token: string | null = null; // Store token from localStorage

  constructor(private userService: UserService, private router: Router) {} //Inject the UserService

  ngOnInit(): void {
    // Fetch the token from localStorage
    this.token = localStorage.getItem('token');

    // Check if token is available
    if (this.token) {
      // Use token to fetch user data or perform other tasks
      this.fetchUserData();
    } else {
      // If no token found, redirect to login page
      this.router.navigate(['../../login-page']);
    }
  }

  fetchUserData(): void {
    const token = localStorage.getItem('token'); // Fetch the token from localStorage
  
    if (token) {
      this.userService.getUserProfileFromToken(token).subscribe(
        (data: any) => {
          console.log('User Profile Data:', data);
          // Add any logic you want to execute after fetching the user profile
          this.ArticleText = data.myArticles[0].text;
        },
        (error) => {
          console.error('Error fetching user data', error);
          // Handle error, potentially navigate back to login
        }
      );
    } else {
      // Handle case where token is missing
      console.error('No token found');
    }
  }
}