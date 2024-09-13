import { Component } from '@angular/core';
import { UserService } from '../../../services/user-service/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule],
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
}
