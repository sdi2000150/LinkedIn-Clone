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

  createArticle(token: string, article: any): Observable<boolean> {

    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
      // 'Content-Type': 'application/json'
    });
    return this.http.post<boolean>(`${this.baseUrl}/user/create_article/${email}`, article, { headers });
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

  // New method to fetch user profile by username
  getUserName(username: string, token: string): Observable<any> {
    const url = `${this.baseUrl}/user/find_${username}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(url, { headers });
  }

  // New method to fetch all users
  getAllUsers(token: string): Observable<any> {
    const url = `${this.baseUrl}/user/all_users`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
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

  // New methods for job functionalities:

  // getAllJobs(): Observable<any> {
  //   return this.http.get(`${this.baseUrl}/jobs`);
  // }

  getJobOffers(token: string): Observable<any> {
      // Extract the email from the token
      const email = this.extractEmailFromToken(token);

      // Set up the headers with the token
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
  
      // Append the email to the URL
      const url = `${this.baseUrl}/user/${email}/job-offers`;
  
      // Send the GET request with the Authorization header
      return this.http.get<any>(url, { headers });
  }
  getAppliedJobs(token: string): Observable<any> {
    // Extract the email from the token
    const email = this.extractEmailFromToken(token);

    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    // Append the email to the URL
    const url = `${this.baseUrl}/user/${email}/applied-jobs`;

    // Send the GET request with the Authorization header
    return this.http.get<any>(url, { headers });
  }

  //TODO
  updateEmail(newEmail: string, token: string): Observable<any> {
    const url = `${this.baseUrl}/user/update_email`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put(url, { email: newEmail }, { headers });
  }
  //TODO
  updatePassword(newPassword: string, token: string): Observable<any> {
    const url = `${this.baseUrl}/user/update_password`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put(url, { password: newPassword }, { headers });
  }

  updateUserProfile(token: string, updateData: any): Observable<any> {
    // Extract the email from the token
    const email = this.extractEmailFromToken(token);

    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<any>(`${this.baseUrl}/user/${email}/profile`, updateData, {
      headers: { Authorization: `Bearer ${token}` }
    });
  }

  getContacts(token: string): Observable<any> {
    // Extract the email from the token
    const email = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    // Append the email to the URL and send the GET request with the Authorization header
    return this.http.get<any>(`${this.baseUrl}/user/${email}/contacts`, { headers });
  }

  getProfileViewByEmail(token: string, email: string): Observable<any> {
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(`${this.baseUrl}/user/view-profile/${email}`, { headers });
  }

  getUsersRelation(token: string, useremail: string): Observable<String> {
    // Extract the email from the token
    const myemail = this.extractEmailFromToken(token);

    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<String>(`${this.baseUrl}/user/identify/${myemail}/${useremail}`, {
      headers,
      responseType: 'text' as 'json' // Tell HttpClient to expect a text response
    });  
  }

  getContactsJobOffers(token: string): Observable<any> {
    // Extract the email from the token
    const email = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    // Append the email to the URL and send the GET request with the Authorization header
    return this.http.get<any>(`${this.baseUrl}/user/${email}/contacts-job-offers`, { headers });
  }

  // Method to fetch my articles
  getMyArticles(token: string): Observable<any[]> {
    // Extract the email from the token
    const email = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    // Append the email to the URL and send the GET request with the Authorization header
    return this.http.get<any[]>(`${this.baseUrl}/user/${email}/my_articles`, { headers });
  }

  // Method to fetch contact articles
  getContactArticles(token: string): Observable<any[]> {
    // Extract the email from the token
    const email = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    // Append the email to the URL and send the GET request with the Authorization header
    return this.http.get<any[]>(`${this.baseUrl}/user/${email}/contact_articles`, { headers });
  }

  // Method to send a new request
  sendNewRequest(token: string, userEmail: string): Observable<void> {
    // Extract the email from the token
    const myEmail = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    // Append the email to the URL and send the GET request with the Authorization header
    return this.http.get<void>(`${this.baseUrl}/user/NewR/${myEmail}/${userEmail}`, { headers });
  }

  // Method to reject a received request
  rejectReceivedRequest(token: string, userEmail: string): Observable<void> {
    // Extract the email from the token
    const myEmail = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    // Append the email to the URL and send the GET request with the Authorization header
    return this.http.get<void>(`${this.baseUrl}/user/RejectReceivedR/${myEmail}/${userEmail}`, { headers });
  }

  // Method to accept a received request
  acceptReceivedRequest(token: string, userEmail: string): Observable<void> {
    // Extract the email from the token
    const myEmail = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    // Append the email to the URL and send the GET request with the Authorization header
    return this.http.get<void>(`${this.baseUrl}/user/AcceptReceivedR/${myEmail}/${userEmail}`, { headers });
  }

  // Method to delete a sent request
  deleteSentRequest(token: string, userEmail: string): Observable<void> {
    // Extract the email from the token
    const myEmail = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    // Append the email to the URL and send the GET request with the Authorization header
    return this.http.get<void>(`${this.baseUrl}/user/DeleteSentR/${myEmail}/${userEmail}`, { headers });
  }

  // needs to be tested and used 
  likeArticle(token: string, articleId: number): Observable<boolean> {
    // Extract the email from the token
    const myEmail = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<boolean>(`${this.baseUrl}/user/${myEmail}/like/${articleId}`, { headers });
  }
  getArticleById(token: string, articleId: number): Observable<any> {
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(`${this.baseUrl}/article/${articleId}`, { headers });
  }

  // applyToJob(userId: number, jobId: number): Observable<any> {
  //   return this.http.post(`${this.baseUrl}/user/${userId}/apply/${jobId}`, {});
  // }
}
