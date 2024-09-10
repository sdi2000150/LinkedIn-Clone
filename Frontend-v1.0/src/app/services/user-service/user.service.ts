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

  // Method to fetch user profile based on JWT token and extracted email
  getUserProfileFromToken(token: string): Observable<any> {
    // Extract the email from the token
    const email = this.extractEmailFromToken(token);

    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    // Append the email to the URL
    const url = `${this.baseUrl}/user/${email}`;

    // Send the GET request with the Authorization header
    return this.http.get<any>(url, { headers });
  }

  // Utility method to extract email from the JWT token
  private extractEmailFromToken(token: string): string | null {
    try {
      const payload = JSON.parse(atob(token.split('.')[1])); // Decode the JWT payload
      if (payload && payload.sub) {
        return payload.sub; // Extract and return the email from the 'sub' field
      } else {
        console.error('Email not found in token');
        return null;
      }
    } catch (error) {
      console.error('Error decoding token or extracting email', error);
      return null;
    }
  }

}
