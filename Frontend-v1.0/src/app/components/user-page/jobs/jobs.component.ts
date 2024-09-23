import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Import CommonModule (for NgFor... usage on the HTML)
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-jobs',
  standalone: true,
  imports: [NavbarComponent, CommonModule, FormsModule],
  templateUrl: './jobs.component.html',
  styleUrl: './jobs.component.css'
})
export class JobsComponent {
  jobOfferTitle: string = ''; // Store the job offer title
  jobOfferNeedOfDegree: boolean = false; // Store the job offer need of a degree
  jobOfferFullTime: boolean = false; // Store the job offer type
  jobOfferSalary: number = 0; // Store the job offer salary
  jobOfferOtherRequirements: string = ''; // Store the job offer other requirements
  
  msgSuccess: string | null = null;
  msgError: string | null = null;

  jobs: any[] = []; // Store job offers of the user
  appliedJobs: any[] = []; // Store jobs the user has applied for
  contactsJobs: any[] = []; // Store job offers of the user's contacts

  needOfDegree: string = 'Empty'; // Store the user's need of a degree
  fulltime: string = 'Empty'; // Store the job type

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
      // Get job offers
      this.userService.getJobOffers(token).subscribe(
        (data: any) => {
          console.log('User Profile Data:', data);
          // Add any logic you want to execute after fetching the user profile
          this.jobs = data;
        },
        (error) => {
          console.error('Error fetching user data', error);
          // Handle error, potentially navigate back to login
        }
      );
      // Get applied jobs
      this.userService.getAppliedJobs(token).subscribe(
        (appliedJobs: any) => {
          console.log('Applied Jobs:', appliedJobs);
          this.appliedJobs = appliedJobs;
        },
        (error) => {
          console.error('Error fetching applied jobs', error);
          // Handle error, potentially navigate back to login
        }
      );
      // Get my contacts' job offers
      this.userService.getContactsJobOffers(token).subscribe(
        (contactsJobOffers: any) => {
          console.log('Contacts Job Offers:', contactsJobOffers);
          this.contactsJobs = contactsJobOffers;
          // Add any logic you want to execute after fetching the user profile
        },
        (error) => {
          console.error('Error fetching contacts job offers', error);
          // Handle error, potentially navigate back to login
        }
      );
    } else {
      // Handle case where token is missing
      console.error('No token found');
    }
  }

  createJobOffer(): void {
    const newjobOffer = {
      title: this.jobOfferTitle,
      needOfDegree: this.jobOfferNeedOfDegree,
      fullTime: this.jobOfferFullTime,
      salary: this.jobOfferSalary,
      otherRequirements: this.jobOfferOtherRequirements
    };

    if (this.token) {
      this.userService.createJobOffer(this.token, newjobOffer).subscribe(
        (response) => {
          if (response) {
            console.log(newjobOffer);
            this.msgSuccess = 'Job offer posted successfully!';
            setTimeout(() => this.msgSuccess = '', 3000);
            this.fetchUserData();
            this.jobOfferTitle = '';
            this.jobOfferNeedOfDegree = false;
            this.jobOfferFullTime = false;
            this.jobOfferSalary = 0;
            this.jobOfferOtherRequirements = '';

          } else {
            this.msgError = 'Failed to post article. Please try again.';
            setTimeout(() => this.msgError = '', 3000);
          }
        },
        (error) => {
          this.msgError = 'Failed to post article. Please try again.';
          setTimeout(() => this.msgError = '', 3000);
        }
      );
    } else {
      this.msgError = 'No token found';
      this.msgSuccess = null;
      console.error('No token found');
    }
  }

  deleteJobOffer(jobID: number): void {
    if (this.token) {
      this.userService.deleteJobOffer(this.token, jobID).subscribe(
        () => {
          console.log('Job offer deleted successfully!');
          setTimeout(() => this.msgSuccess = '', 3000);
          this.fetchUserData();
        },
        (error) => {
          setTimeout(() => this.msgError = '', 3000);
          console.error('Error deleting job offer', error);
        }
      );
    } else {
      this.msgError = 'No token found';
      this.msgSuccess = null;
      console.error('No token found');
    }
  }


  applyToJob(jobId: number): void {
    // Fetch the token from localStorage
    this.token = localStorage.getItem('token');

    
    if (this.token) {
      this.userService.applyToJob(this.token, jobId).subscribe(
        (response: boolean) => {
          if (this.token && response) {
            this.msgSuccess = 'Job Application added successfully!';
            console.log('Job Application added successfully');

            this.ngOnInit(); // Refresh the jobs after applying to job

          } else { //response is false
            // console.error('Failed to apply to job');
            this.msgError = 'User already applied to this job';
            console.log('User already applied to this job');
          }
        },
        (error) => {
          console.error('Error applying to job', error);
        }
      );
    }
  }

  deleteJobApplication(jobID: number): void {
    if (this.token) {
      this.userService.deleteJobApplication(this.token, jobID).subscribe(
        (response) => {
            console.log('Job Application deleted successfully');
            // Refresh the page after deleting the comment
            this.ngOnInit();
        },
        (error) => {
          console.error('Error deleting jobApplication', error);
        }
      );
    }
  }

}
