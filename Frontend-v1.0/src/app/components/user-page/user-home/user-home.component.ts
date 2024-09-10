import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Import CommonModule (for NgFor... usage on the HTML)
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';

@Component({
  selector: 'app-user-home',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    // this.getStudents(); //load students on initialization
  }

}