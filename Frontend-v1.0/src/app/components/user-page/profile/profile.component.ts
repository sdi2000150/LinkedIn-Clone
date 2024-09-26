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

  CVfile: string = 'Upload your CV file (only .pdf supported):'; //Placeholder for the user's CV file

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
  cvFile: File | null = null; // Add a field for the CV file

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
          if (data.cvFileUrl) {
            // Just show a different text on top of the CV upload
            this.CVfile = "Update your CV file (only .pdf supported), or download the existing one:";
          }
          if (data.coverPhotoUrl) {
            this.userService.downloadCoverPhoto(token, '').subscribe(
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
            this.userService.downloadProfilePhoto(token, '').subscribe(
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

      const formData = new FormData();
      if (this.coverPhotoFile) {
        formData.append('image', this.coverPhotoFile);
      }
  
      if (this.token) {
        this.userService.updateCoverPhoto(this.token, formData).subscribe(
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

      const formData = new FormData();
      if(this.profilePhotoFile) {
        formData.append('image', this.profilePhotoFile);
      }

      if (this.token) {
        this.userService.updateProfilePhoto(this.token, formData).subscribe(
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
  
  onCVSelected(event: any): void {
    if (event.target.files && event.target.files[0]) {
      this.cvFile = event.target.files[0];

      const formData = new FormData();
      if (this.cvFile) {
        formData.append('file', this.cvFile);
      }

      if (this.token) {
        this.userService.updateCV(this.token, formData).subscribe(
          (response) => {
            console.log('CV uploaded successfully', response);
            // Refresh the page
            this.ngOnInit();
          },
          (error) => {
            console.error('Error uploading CV', error);
          }
        );
      }
    } else {
      console.error('No CV selected');
    }
  }

  downloadCV(): void {
    if (this.CVfile === 'Upload your CV file (only .pdf supported):') {
      console.log("No CV available for download");
    } else {
      if (this.token) {
          this.userService.downloadCV(this.token, '').subscribe(
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
