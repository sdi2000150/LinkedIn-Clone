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
  private baseUrl = 'http://localhost:8080'; // base URL for backend springboot
  

  constructor(private http: HttpClient) { }


  getUser(): Observable<any> {
    const url = `${this.baseUrl}/user/1`; // For now, just fetch user

    return this.http.get(url);
  }


  // THE FOLLOWING WERE FOR THE "security-demo" BACKEND CONNECTION:

  // private baseUrl = 'http://localhost:8080/auth'; // base URL for backend springboot

  // constructor(private http: HttpClient) { }

  signup(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/addNewUser`, user, { responseType: 'text' }); // The backend returns as a plain string a "User Added Successfully"
  }

  login(authRequest: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/generateToken`, authRequest, {
      responseType: 'text', // The backend returns the JWT as a plain string
    });
  }

  getUserProfile(role: string): Observable<any> {
    // const url = role === 'ROLE_ADMIN' ? 
    //             `${this.baseUrl}/admin/adminProfile` : 
    //             `${this.baseUrl}/user/userProfile`;
    const url = `${this.baseUrl}/user/1`; // For now, just fetch user

    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + localStorage.getItem('token') // Use token from localStorage
  });

    return this.http.get(url, { headers, responseType: 'text' }); // Expecting plain text response
  }

}
