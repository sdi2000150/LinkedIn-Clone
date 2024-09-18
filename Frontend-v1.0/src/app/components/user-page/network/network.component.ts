import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { UserService } from '../../../services/user-service/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-network',
  standalone: true,
  imports: [NavbarComponent, CommonModule, FormsModule, RouterModule],
  templateUrl: './network.component.html',
  styleUrl: './network.component.css'
})
export class NetworkComponent {
  searchQuery: string = '';
  searchResults: { username: string, email: string }[] = [];

  contacts: { username: string, email: string }[] = [];

  token: string = localStorage.getItem('token') || ''; // Store token from localStorage

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.loadContacts();
  }

  loadContacts() {
    this.userService.getContacts(this.token).subscribe(results => {
      this.contacts = results.map((result: any) => ({
        username: result[0],
        email: result[1]
      }));
    });
  }

  onSearch() {
    const username = this.searchQuery;
    this.userService.getUserName(username, this.token).subscribe(results => {
      this.searchResults = results.map((result: any) => ({
        username: result[0],
        email: result[1]
      }));
    });
  }
}
