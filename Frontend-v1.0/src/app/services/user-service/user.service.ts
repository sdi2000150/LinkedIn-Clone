import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

// Define HTTP options with headers
const httpOptions = {
  headers: new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/students'; // base URL for backend springboot

  constructor(private http: HttpClient) { }

  // Method to GET all students
  getStudents(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  // Method to GET a student by ID
  getStudentById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  // Method to POST a new student
  addStudent(student: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, student, httpOptions);
  }

  // Method to PUT (update) an existing student
  updateStudent(id: number, student: any): Observable<string> {
    return this.http.put(`${this.baseUrl}/${id}`, student, { responseType: 'text', ...httpOptions });
  }

  // Method to DELETE a student
  deleteStudent(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}