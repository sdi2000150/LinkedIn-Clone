import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Import CommonModule (for NgFor... usage on the HTML)
import { NavbarComponent } from '../navbar/navbar.component';
import { FormsModule } from '@angular/forms'; // Import FormsModule (for forms usage)
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

  articlePhotoFile: File | null = null; // Store, and initialize, the new article's photo file

  token: string | null = null; // Store token from localStorage

  constructor(private userService: UserService, private router: Router) {} // Inject the UserService

  ngOnInit(): void {
    // Fetch the token from localStorage
    this.token = localStorage.getItem('token');

    // Check if token is available
    if (this.token) {
      // Use token to fetch user data
      this.fetchUserData();
    } else {
      // If no token found, redirect to login page
      console.error('No token found');
      this.router.navigate(['../../login-page']);
    }
  }

  fetchUserData(): void {
    const token = localStorage.getItem('token'); // Fetch the token from localStorage
  
    if (token) {  // Checking token again, to avoid warning of null token
      this.userService.getUserProfileFromToken(token).subscribe(
        (data: any) => {
          console.log('User Profile Data:', data);
          this.articles = data.myArticles;        
        },
        (error) => {
          console.error('Error fetching user data', error);
        }
      );
    } else {
      // If no token found, redirect to login page
      console.error('No token found');
      this.router.navigate(['../../login-page']);
    }
  }
  
  // Create a new FormData object to store the article photo
  formData = new FormData();
  // Function to handle the selection of a new article photo, asychronized with the article creation
  onArticlePhotoSelected(event: any): void {
    if (event.target.files && event.target.files[0]) {  // Check if a file is selected (will be the first and only file)
      this.articlePhotoFile = event.target.files[0];    // Store the selected file

      if(this.articlePhotoFile) {
        this.formData.append('image', this.articlePhotoFile); // Append the file to the formData object, with the field name 'image'
      }
    } else {
      console.error('No profile photo selected');
    }
  }

  // Function to create a new article
  createArticle(): void {
    // Create a new article object (json), with just the field text
    const newArticle = {
      text: this.Text
    };

    if (this.token) {
      // Create the article
      this.userService.createArticle(this.token, newArticle).subscribe(
        (response: number) => {
            if (this.token && this.formData.has('image')) { // If a photo was selected (asynchronously)
              // Update the article with photo, giving the article id (response)
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
            this.fetchUserData(); // Refresh the user data
            this.Text = '';     // Clear the text field of the article oject
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