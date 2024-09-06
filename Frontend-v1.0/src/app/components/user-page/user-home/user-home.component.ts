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

  // THESE WERE FOR THE "demo" BACKEND CONNECTION:
  
  // students: any[] = []; // Store fetched students

  // //hardcoded students for testing
  // hardcodedStudent = { name: 'Teo Mor', age: 22, email: 'teomor@di.gr' };
  // studentToUpdate = { id: 1, name: 'Updated Name', age: 23, email: 'updatedname@di.gr' };
  // studentIdToDelete = 1;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    // this.getStudents(); //load students on initialization
  }

  // // Method to get students
  // getStudents(): void {
  //   this.userService.getStudents().subscribe(
  //     response => {
  //       console.log('Students:', response);
  //       // this.students = response; // Store the response in students array
  //     },
  //     error => {
  //       console.error('Error fetching students:', error);
  //     }
  //   );
  // }

  // // Method to get a specific student by ID
  // getSpecificStudent(): void {
  //   const studentId = 1; //hardcoded ID for testing
  //   this.userService.getStudentById(studentId).subscribe(
  //     response => {
  //       console.log('Student:', response);
  //     },
  //     error => {
  //       console.error('Error fetching specific student:', error);
  //     }
  //   );
  // }

  // // Method to add a hardcoded student
  // addStudent(): void {
  //   this.userService.addStudent(this.hardcodedStudent).subscribe(
  //     response => {
  //       console.log('Student added:', response);
  //       // this.getStudents(); // Refresh the student list after adding
  //     },
  //     error => {
  //       console.error('Error adding student:', error);
  //     }
  //   );
  // }

  // // Method to update a hardcoded student
  // updateStudent(): void {
  //   this.userService.updateStudent(this.studentToUpdate.id, this.studentToUpdate).subscribe(
  //     response => {
  //       console.log('Student updated:', response); // The response should be plain text
  //       // Optionally refresh the student list after updating
  //     },
  //     error => {
  //       console.error('Error updating student:', error);
  //     }
  //   );
  // }

  // // Method to delete a hardcoded student
  // deleteStudent(): void {
  //   this.userService.deleteStudent(this.studentIdToDelete).subscribe(
  //     response => {
  //       console.log('Student deleted:', response);
  //       // this.getStudents(); // Refresh the student list after deletion
  //     },
  //     error => {
  //       console.error('Error deleting student:', error);
  //     }
  //   );
  // }
}