import { Component } from '@angular/core';
import { UserService } from '../../../services/user-service/user.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; // Import RouterModule

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule, RouterModule], // Add RouterModule to the imports array
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.css'
})
export class UsersListComponent {
  users: { username: string, email: string }[] = [];
  token: string = localStorage.getItem('token') || '';

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.userService.getAllUsers(this.token).subscribe(results => {
      this.users = results.map((result: any) => ({
        username: result[0],
        email: result[1]
      }));
    });
  }
  
  exportUserData(user: { username: string, email: string }, format: string) {

    if (format === 'json') {
      // JSON fetch and download:
      this.userService.getUserDataJson(this.token, user.email).subscribe(data => {
        // Convert the data to a JSON string with pretty formatting (2 spaces) 
        const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
        // Create a URL for the blob
        const url = window.URL.createObjectURL(blob);
        // Create an anchor element, which will be used to download the file
        const a = document.createElement('a');
        a.href = url;
        // Set the download attribute to the file name
        a.download = `${user.username}.json`;
        // Simulate a click on the anchor element
        a.click();
        // Revoke the URL to prevent memory leaks(?)
        window.URL.revokeObjectURL(url);
      });
    } else if (format === 'xml') {
      // XML not working, needs Backend fix, and possible fix here too
      this.userService.getUserDataXml(this.token, user.email).subscribe(data => {
        const blob = new Blob([data], { type: 'application/xml' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `${user.username}.xml`;
        a.click();
        window.URL.revokeObjectURL(url);
      });
    }
  }

}
