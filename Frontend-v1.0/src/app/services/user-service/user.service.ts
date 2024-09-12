import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

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

  
  signup(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/addNewUser`, user, { responseType: 'text' }); // The backend returns as a plain string a "User Added Successfully"
  }

  login(authRequest: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/generateToken`, authRequest, {
      responseType: 'text', // The backend returns the JWT as a plain string
    });
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

  isUserAdmin(token: string): Observable<boolean> {
      const email = this.extractEmailFromToken(token);
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
      const url = `${this.baseUrl}/user/${email}`;

    // Return an observable that checks if the user is an admin
    return this.http.get<any>(url, { headers }).pipe(
      map((data: any) => {
        if (data.role === 'ROLE_ADMIN') {
          // User is an admin
          return true;
        } else {
          // User is not an admin
          return false;
        }
      })
    );
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

  isTokenExpired(token: string): boolean {
    const payload = JSON.parse(atob(token.split('.')[1])); // Decode the JWT payload
    const expiry = payload.exp; // Extract the expiry time from the 'exp' field
    const now = Math.floor(Date.now() / 1000); // Get the current time in seconds
    return now >= expiry; // Return if the token is expired
  }

  // New methods for job functionalities
  getAllJobs(): Observable<any> {
    return this.http.get(`${this.baseUrl}/jobs`);
  }

  getAppliedJobs(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/user/${userId}/applied-jobs`);
  }

  applyToJob(userId: number, jobId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/user/${userId}/apply/${jobId}`, {});
  }
}
