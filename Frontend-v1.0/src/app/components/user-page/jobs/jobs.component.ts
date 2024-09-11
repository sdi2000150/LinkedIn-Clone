import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Import CommonModule (for NgFor... usage on the HTML)

@Component({
  selector: 'app-jobs',
  standalone: true,
  imports: [NavbarComponent, CommonModule],
  templateUrl: './jobs.component.html',
  styleUrl: './jobs.component.css'
})
export class JobsComponent {
  jobs: any[] = []; // Store articles of the user

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
          this.jobs = data.myJobs;
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
