import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [NavbarComponent, CommonModule, RouterModule],
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent implements OnInit {
  usersRequestingMe: any[] = [];
  myRequests: any[] = [];
  
  token: string = localStorage.getItem('token') || ''; // Store token from localStorage

  button1: string = 'Empty';
  button2: string = 'Empty';
  button3: string = 'Empty';
  button4: string = 'Empty';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.fetchUsersRequestingMe();
    this.fetchMyRequests();
  }

  fetchUsersRequestingMe(): void {
    // Fetch the token from localStorage
    const token = localStorage.getItem('token');
    if (token) {
      this.userService.getUsersRequestingMe(token).subscribe(data => {
        this.usersRequestingMe = data;
        this.usersRequestingMe.forEach(user => this.fetchButtons("received"));
      },
      error => {
        console.error('Error fetching users requesting me:', error);
      });
    } else {
      console.error('Token not found in localStorage');
    }
  }

  fetchMyRequests(): void {
    // Fetch the token from localStorage
    const token = localStorage.getItem('token');
    if(token) {
      this.userService.getMyRequests(token).subscribe(data => {
        this.myRequests = data;
        this.myRequests.forEach(user => this.fetchButtons("sent"));
      },
      error => {
        console.error('Error fetching my requests:', error);
      });
    } else {
      console.error('Token not found in localStorage');
    }
  }

  fetchButtons(type: string): void {
    // Logic to set button1 and button2 based on the user
    if (type === 'received') {
      this.button1 = 'Accept Request';
      this.button2 = 'Decline Request';
    } else if (type === 'sent') {
      this.button3 = 'Cancel Request';
    }
  }

  handleButton1Click(userEmail: string): void {
    const token = localStorage.getItem('token'); // Fetch the token from localStorage

    if (token && userEmail) {
      if (this.button1 === 'Accept Request') {
        this.userService.acceptReceivedRequest(token, userEmail).subscribe(() => {
          this.ngOnInit(); // Refresh the page
        });
      }
    }
  }

  handleButton2Click(userEmail: string): void {
    const token = localStorage.getItem('token'); // Fetch the token from localStorage

    if (token && userEmail) {
      if (this.button2 === 'Decline Request') {
        this.userService.rejectReceivedRequest(token, userEmail).subscribe(() => {
          this.ngOnInit(); // Refresh the page
        });
      }
    }
  }

  handleButton3Click(userEmail: string): void {
    const token = localStorage.getItem('token'); // Fetch the token from localStorage

    if (token && userEmail) {
      if (this.button3 === 'Cancel Request') {
        this.userService.deleteSentRequest(token, userEmail).subscribe(() => {
          this.ngOnInit(); // Refresh the page
        });
      }
    }
  }
}
