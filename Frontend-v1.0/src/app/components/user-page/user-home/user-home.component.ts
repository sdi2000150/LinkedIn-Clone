import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Import CommonModule (for NgFor... usage on the HTML)
import { NavbarComponent } from '../navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user-service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-home',
  standalone: true,
  imports: [FormsModule, CommonModule, NavbarComponent],
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  articles: any[] = []; // Store articles of the user
  Text: string = '';
  msgError: string = '';
  msgSuccess: string = '';

  articlePhotoFile: File | null = null;

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
          this.articles = data.myArticles;        
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
  
  formData = new FormData();

  onArticlePhotoSelected(event: any): void {
    if (event.target.files && event.target.files[0]) {
      this.articlePhotoFile = event.target.files[0];

      // const formData = new FormData();
      if(this.articlePhotoFile) {
        this.formData.append('image', this.articlePhotoFile);
      }
    } else {
      console.error('No profile photo selected');
    }
  }

  createArticle(): void {
    const newArticle = {
      text: this.Text
    };

    if (this.token) {
      this.userService.createArticle(this.token, newArticle).subscribe(
        (response: number) => {
            if (this.token && this.formData.has('image')) {
              this.userService.updateArticlePhoto(this.token, this.formData, response).subscribe(
                (updateResponse) => {
                  console.log('Photo uploaded successfully');
                  this.formData = new FormData(); // Clear formData after use
                },
                (updateError) => {
                  console.error('Error uploading photo', updateError);
                }
              );
            }
            console.log(newArticle);
            this.msgSuccess = 'Article posted successfully!';
            setTimeout(() => this.msgSuccess = '', 3000);
            this.fetchUserData();
            this.Text = '';
        },
        (error) => {
          this.msgError = 'Failed to post article. Please try again.';
          setTimeout(() => this.msgError = '', 3000);
        }
      );
    } else {
      console.error('No token found');
    }
  }

}