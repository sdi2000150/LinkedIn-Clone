import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserService } from '../../../services/user-service/user.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  userName: string = 'Empty Name'; //Placeholder for the user's name

  constructor(private userService: UserService) {} //Inject the UserService

  ngOnInit(): void {
    //(initial fetch can be done here if needed)
    this.userService.getUser().subscribe(
      //print json data here
      (data) => {
        this.userName = data.name;
        console.log(data);
      }
    );
  }

  fetchUserData(): void {
    this.userService.getUser().subscribe(
      //print json data here
      (data) => {
        console.log(data);
      }
    );
  }
}
