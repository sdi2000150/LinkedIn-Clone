import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../../services/user-service/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-user-view',
  standalone: true,
  imports: [],
  templateUrl: './user-view.component.html',
  styleUrl: './user-view.component.css'
})
export class UserViewComponent implements OnInit {

  Name: string = 'Empty'; //Placeholder for the user's name
  Surname: string = 'Empty'; //Placeholder for the user's surname
  Email: string = 'Empty'; //Placeholder for the user's email
  Phone: string = 'Empty'; //Placeholder for the user's phone number
  Birthdate: string = 'Empty'; //Placeholder for the user's birthdate

  token: string | null = null; // Store token from localStorage

  coverPhotoUrl: string = 'assets/images/placeholder-cover.jpg'; // Default cover photo URL
  profilePhotoUrl: string = 'assets/images/placeholder-profile.jpg'; // Default profile photo URL

  CVfile: string = 'No CV available for download'; //Placeholder for the user's CV file

  about: string = ''; // Field for about
  selectedExperience: string = '';
  experienceDescription: string = ''; // Field for experience description
  selectedEducation: string = '';
  educationDescription: string = ''; // Field for education description
  selectedSkills: string = '';  //this will change to string[] = []
  
  constructor(private userService: UserService, private router: Router, private route: ActivatedRoute) {} //Inject the UserService

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
    
    const email = this.route.snapshot.paramMap.get('email');  // Fetch the email from frontend URL

    if (token) {
      if (email) {
        this.userService.getProfileViewByEmail(token, email).subscribe(
          (data: any) => {
            console.log('User Profile Data:', data);
            this.Name = data.name;
            this.Surname = data.lastname;
            this.Email = data.email;
            this.Phone = data.phone;
            this.Birthdate = data.birthdate;
            this.about = data.about;
            this.selectedExperience = data.experience;
            this.experienceDescription = data.experienceDescription;
            this.selectedEducation = data.education;
            this.educationDescription = data.educationDescription;
            this.selectedSkills = data.skills;
            if (data.cvFileUrl) {
              // Just show a different text on top of the CV upload
              this.CVfile = "Download the user's CV file:";
            }
            if (data.coverPhotoUrl) {
              this.userService.downloadCoverPhoto(token, this.Email).subscribe(
                (response) => {
                  // Convert the image to base64
                  const reader = new FileReader();
                  reader.readAsDataURL(response);
                  reader.onloadend = () => {
                    this.coverPhotoUrl = reader.result as string;
                  };
                },
                (error) => {
                  console.error('Error fetching cover photo', error);
                }
              );
            }
            if (data.profilePhotoUrl) {
              this.userService.downloadProfilePhoto(token, this.Email).subscribe(
                (response) => {
                  // Convert the image to base64
                  const reader = new FileReader();
                  reader.readAsDataURL(response);
                  reader.onloadend = () => {
                    this.profilePhotoUrl = reader.result as string;
                  };
                },
                (error) => {
                  console.error('Error fetching profile photo', error);
                }
              );
            }
          },
          (error) => {
            console.error('Error fetching user data: userdata', error);
            // Handle error, potentially navigate back to user-page
            this.router.navigate(['../../user-page']);
          }
        );
      } else {
        console.error('No email found in the URL');
      }
    } else {
      // Handle case where token is missing
      console.error('No token found');
    }
  }

  downloadCV(): void {
    if (this.CVfile === 'No CV available for download') {
      console.log("No CV available for download");
    } else {
      if (this.token) {
          this.userService.downloadCV(this.token, this.Email).subscribe(
              (response) => {
                  // Create a URL for the response blob
                  const url = window.URL.createObjectURL(response);
                  // Create an anchor element, which will be used to download the file
                  const a = document.createElement('a');
                  a.href = url;
                  // Set the download attribute to the file name
                  a.download = 'CV.pdf';
                  // Simulate a click on the anchor element
                  a.click();
                  // Revoke the URL to prevent memory leaks(?)
                  window.URL.revokeObjectURL(url);
              },
              (error) => {
                  console.error('Error fetching CV file', error);
              }
          );
      }
    }
  }
}