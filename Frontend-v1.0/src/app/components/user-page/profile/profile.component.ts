import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';
import { FormsModule } from '@angular/forms'; // Import FormsModule
import { CommonModule } from '@angular/common'; // Import CommonModule
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavbarComponent, FormsModule, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  Name: string = 'Empty'; //Placeholder for the user's name
  Surname: string = 'Empty'; //Placeholder for the user's surname
  Email: string = 'Empty'; //Placeholder for the user's email
  Phone: string = 'Empty'; //Placeholder for the user's phone number
  Birthdate: string = 'Empty'; //Placeholder for the user's birthdate

  token: string | null = null; // Store token from localStorage

  coverPhotoUrl: string = 'assets/images/placeholder-cover.jpg'; // Default cover photo URL
  profilePhotoUrl: string = 'assets/images/placeholder-profile.jpg'; // Default profile photo URL

  experiences: string[] = ['INTERN', 'JUNIOR', 'MID_LEVEL', 'SENIOR', 'LEAD', 'MANAGER', 'DIRECTOR', 'EXECUTIVE'];
  educations: string[] = ['HIGH_SCHOOL', 'ASSOCIATE_DEGREE', 'BACHELORS_DEGREE', 'MASTERS_DEGREE', 'DOCTORATE', 'PROFESSIONAL_CERTIFICATION'];
  skills: string[] = [   
    'PROGRAMMING',
    'DATA_ANALYSIS',
    'PROJECT_MANAGEMENT',
    'COMMUNICATION',
    'PROBLEM_SOLVING',
    'TEAMWORK',
    'LEADERSHIP',
    'DESIGN',
    'MARKETING',
    'SALES',
    'CUSTOMER_SERVICE',
    'FINANCE',
    'OPERATIONS',
    'STRATEGIC_PLANNING',
    'RESEARCH'
  ];

  about: string = ''; // Field for about

  selectedExperience: string = '';
  experienceDescription: string = ''; // Field for experience description
  selectedEducation: string = '';
  educationDescription: string = ''; // Field for education description
  selectedSkills: string = '';  //this will change to string[] = []
  
  profilePhotoFile: File | null = null;
  coverPhotoFile: File | null = null;

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
          if (data.coverPhotoUrl) {
            this.coverPhotoUrl = data.coverPhotoUrl;
          }
          if (data.profilePhotoUrl) {
            this.profilePhotoUrl = data.profilePhotoUrl;
          }
          // Add any logic you want to execute after fetching the user profile
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

  updateUserProfile(): void {
    if (this.token) {
      const updateData = {
        name: this.Name,
        lastname: this.Surname,
        email: this.Email,
        phone: this.Phone,
        birthdate: this.Birthdate,
        about: this.about,
        experience: this.selectedExperience,
        experienceDescription: this.experienceDescription,
        education: this.selectedEducation,
        educationDescription: this.educationDescription,
        skills: this.selectedSkills
      };

      this.userService.updateUserProfile(this.token, updateData).subscribe(
        (response) => {
          console.log('User profile updated successfully', response);
        },
        (error) => {
          console.error('Error updating user profile', error);
        }
      );
    }
  }

  updateAbout(): void {
    if (this.token) {
      const updateData = {
        about: this.about
      };

      this.userService.updateUserProfile(this.token, updateData).subscribe(
        (response) => {
          console.log('User profile updated successfully', response);
        },
        (error) => {
          console.error('Error updating user profile', error);
        }
      );
    }
  }

  updateExperience(): void {
    this.updateUserProfile();
  }

  updateEducation(): void {
    this.updateUserProfile();
  }

  //this will change to access array of strings
  updateSkills(): void {
    this.updateUserProfile();
  }
  

  onCoverPhotoSelected(event: any): void {
    if (event.target.files && event.target.files[0]) {
      this.coverPhotoFile = event.target.files[0];
      if (this.coverPhotoFile) {
        const coverPhotoFileName = this.coverPhotoFile.name;
        this.coverPhotoUrl = `assets/database_photos/covers/${coverPhotoFileName}`;
      }
  
      const updateData = {
        coverPhotoUrl: this.coverPhotoUrl
      };
  
      if (this.token) {
        this.userService.updateUserProfile(this.token, updateData).subscribe(
          (response) => {
            console.log('User files updated successfully', response);
            // Refresh the page
            this.ngOnInit();
          },
          (error) => {
            console.error('Error updating user files', error);
          }
        );
      }
    } else {
      console.error('No cover photo selected');
    }
  }
  
  onProfilePhotoSelected(event: any): void {
    if (event.target.files && event.target.files[0]) {
      this.profilePhotoFile = event.target.files[0];
      if (this.profilePhotoFile) {
        const profilePhotoFileName = this.profilePhotoFile.name;
        this.profilePhotoUrl = profilePhotoFileName;
        this.profilePhotoUrl = `assets/database_photos/profiles/${profilePhotoFileName}`;
      }
  
      const updateData = {
        profilePhotoUrl: this.profilePhotoUrl
      };
  
      if (this.token) {
        this.userService.updateUserProfile(this.token, updateData).subscribe(
          (response) => {
            console.log('User files updated successfully', response);
            // Refresh the page
            this.ngOnInit();
          },
          (error) => {
            console.error('Error updating user files', error);
          }
        );
      }
    } else {
      console.error('No profile photo selected');
    }
  }

}
