import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router'; // Import ActivatedRoute
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';
import { FormsModule } from '@angular/forms'; // Import FormsModule
import { CommonModule } from '@angular/common'; // Import CommonModule
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-profile-view',
  standalone: true,
  imports: [NavbarComponent, FormsModule, CommonModule, RouterModule],
  templateUrl: './profile-view.component.html',
  styleUrl: './profile-view.component.css'
})
export class ProfileViewComponent implements OnInit {
  button1: string = 'Empty';
  button2: string = 'Empty';

  Name: string = 'Empty'; //Placeholder for the user's name
  Surname: string = 'Empty'; //Placeholder for the user's surname
  Email: string = 'Empty'; //Placeholder for the user's email
  Phone: string = 'Empty'; //Placeholder for the user's phone number
  Birthdate: string = 'Empty'; //Placeholder for the user's birthdate

  token: string | null = null; // Store token from localStorage

  coverPhotoUrl: string = 'assets/images/placeholder-cover.jpg'; // Default cover photo URL
  profilePhotoUrl: string = 'assets/images/placeholder-profile.jpg'; // Default profile photo URL

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
      this.fetchButtons();
    } else {
      // If no token found, redirect to login page
      this.router.navigate(['../../login-page']);
    }


  }

  fetchButtons(): void {
    const token = localStorage.getItem('token'); // Fetch the token from localStorage

    const email = this.route.snapshot.paramMap.get('email'); // Fetch the email from the URL

  
    if (token) {
      if (email) {
        this.userService.getUsersRelation(token, email).subscribe(
          (data: any) => {
            // logic here, data is the response from the server, and i one string ou of:
            // 'Connected', 'Request Sent', 'Got Request', 'Sent Request'
            if (data == 'Connected') {
              this.button1 = data;
              this.button2 = 'Disconnect';
            } else if (data == 'Request Sent') {
              this.button1 = data;
              this.button2 = 'Cancel Request';
            } else if (data == 'Got Request') {
              this.button1 = 'Accept Request';
              this.button2 = 'Decline Request';
            } else if (data == 'Sent Request') {
              this.button1 = 'Connect';
              this.button2 = ':)';
            }
          },
          (error) => {
            console.error('Error fetching user data', error);
            // Handle error, potentially navigate back to login
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

  fetchUserData(): void {
    const token = localStorage.getItem('token'); // Fetch the token from localStorage
    
    const email = this.route.snapshot.paramMap.get('email'); // Fetch the email from the URL

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
            // Add any logic you want to execute after fetching the user profile
          },
          (error) => {
            console.error('Error fetching user data', error);
            // Handle error, potentially navigate back to login
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

}
