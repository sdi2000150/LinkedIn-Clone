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

  newComments: { [key: number]: string } = {}; // Store comment text for each article (based on key: articleId)
  currentUser: any = {}; // Store the current user information

  articlePhotoFile: File | null = null; // Store, and initialize, the new article's photo file

  token: string | null = null; // Store token from localStorage

  constructor(private userService: UserService, private router: Router) {} // Inject the UserService

  ngOnInit(): void {
    // Fetch the token from localStorage
    this.token = localStorage.getItem('token');

    // Check if token is available
    if (this.token) {
      this.userService.getUserProfileFromToken(this.token).subscribe(
        (user) => {
          this.currentUser = user;
        },
        (error) => {
          console.error('Error fetching current user', error);
        }
      );
      // Fetch contact articles using the token
      this.userService.getRecommendedArticles(this.token).subscribe(
        (data) => {
          this.articles = data;
          this.articles.forEach(article => {
            this.loadArticlePhoto(article);
          });
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

  loadArticlePhoto(article: any): void {
    if (this.token) {
      if (article.photoUrl) {
        this.userService.downloadArticlePhoto(this.token, article.articleID).subscribe(
          (blob) => {
            // Convert the image to base64
            const reader = new FileReader();
            reader.readAsDataURL(blob);
            reader.onloadend = () => {
              article.photoUrl = reader.result as string;
            };
          },
          (error) => {
            console.error('Error fetching article photo', error);
          }
        );
      }
    }
  }

  likeArticle(articleId: number): void {
    // Fetch the token from localStorage
    this.token = localStorage.getItem('token');

    if (this.token) {
      this.userService.likeArticle(this.token, articleId).subscribe(
        (response: boolean) => {
          if (response) {
            console.log('Article liked successfully');

            this.ngOnInit(); // Refresh the articles after liking an article

          } else {
            console.log('Unliked article');
            
            // Do the local refresh of the updated article
            this.ngOnInit(); // Refresh the articles after liking an article
          }
        },
        (error) => {
          console.error('Error liking article', error);
        }
      );
    } else {
      // If no token found, redirect to login page
      this.router.navigate(['../../login-page']);
    }
  }


  addComment(articleId: number): void {
    // Fetch the token from localStorage
    this.token = localStorage.getItem('token');

    const commentText = this.newComments[articleId];
    const newComment = { 
      content: commentText
    };
    
    if (this.token) {
      this.userService.createComment(this.token, newComment, articleId).subscribe(
        (response: boolean) => {
          if (this.token && response) {
            console.log('Comment added successfully');

            this.ngOnInit(); // Refresh the articles after adding a comment
            this.newComments[articleId] = ''; // Clear the input field after adding the comment

          } else {
            console.error('Failed to add comment');
          }
        },
        (error) => {
          console.error('Error adding comment', error);
        }
      );
    }
  }

  deleteComment(articleId: number, commentId: number): void {
    if (this.token) {
      this.userService.deleteComment(this.token, commentId).subscribe(
        (response) => {
            console.log('Comment deleted successfully');
            // Refresh the page after deleting the comment
            this.ngOnInit();
        },
        (error) => {
          console.error('Error deleting comment', error);
        }
      );
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
            this.ngOnInit(); // Refresh the user data
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