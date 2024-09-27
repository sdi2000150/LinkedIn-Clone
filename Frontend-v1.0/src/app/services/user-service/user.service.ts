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
  // private baseUrl = 'http://localhost:8080'; // base URL for backend springboot (before securing it)
  private baseUrl = 'https://localhost:8443'; // base URL for backend springboot (updated with SSL/TLS security)

  constructor(private http: HttpClient) { }

  ///////////////////////////////////////// Signup/Login methods /////////////////////////////////////////
  // Method for new user registration
  signup(user: any): Observable<boolean> {
    return this.http.post<boolean>(`${this.baseUrl}/auth/addNewUser`, user);
  }

  // Method for user authentication/authorization and login
  login(authRequest: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/generateToken`, authRequest, {
      responseType: 'text', // The backend returns the JWT as a plain string
    });
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////////////


  ////////////////////////////////////////// General methods /////////////////////////////////////////////
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

  // Utility method to check if the JWT token is expired
  isTokenExpired(token: string): boolean {
    const payload = JSON.parse(atob(token.split('.')[1])); // Decode the JWT payload
    const expiry = payload.exp; // Extract the expiry time from the 'exp' field
    const now = Math.floor(Date.now() / 1000); // Get the current time in seconds
    return now >= expiry; // Return if the token is expired
  }
  
  // Method to check if the user is an admin or not
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
  ////////////////////////////////////////////////////////////////////////////////////////////////////////


  //////////////////////////////////////////// Admin methods /////////////////////////////////////////////
  // Method to fetch user data in JSON format
  getUserDataJson(token: string, email: string): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Accept': 'application/json'
    });
    return this.http.get<any>(`${this.baseUrl}/user/${email}/all-data/json`, { headers });
  }
  // Method to fetch user data in XML format
  getUserDataXml(token: string, email: string): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Accept': 'application/xml'
    });
    return this.http.get(`${this.baseUrl}/user/${email}/all-data/xml`, { headers });
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////////////


  //////////////////////////////////////////// User methods //////////////////////////////////////////////
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
  
  // Method to fetch another user's profile based on email given
  getProfileViewByEmail(token: string, email: string): Observable<any> {
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(`${this.baseUrl}/user/view-profile/${email}`, { headers });
  }

  // Method to fetch user profile by username
  getUserName(username: string, token: string): Observable<any> {
    const url = `${this.baseUrl}/user/find_${username}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(url, { headers });
  }

  // Method to fetch all users
  getAllUsers(token: string): Observable<any> {
    const url = `${this.baseUrl}/user/all_users`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(url, { headers });
  }

  // Method to update user profile (all data fields, except: email, password, role, photos, CV)
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

  // Method to update user email and password
  updateEmailPassword(token: string, oldPassword: string, newEmail: string, newPassword: string): Observable<boolean> {
    // Extract the email from the token
    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    const body = {
      OldPassword: oldPassword,
      NewEmail: newEmail,
      NewPassword: newPassword
    };
    return this.http.put<boolean>(`${this.baseUrl}/user/ChangeEmailPassword/${email}`, body, { headers });
  }

  // Method to update user profile photo
  updateProfilePhoto(token:string, formData: FormData): Observable<any> {
    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
      // 'Content-Type': 'application/json'
    });
    return this.http.post<any>(`${this.baseUrl}/user/${email}/uploadProfilePhoto`, formData, { headers });
  }

  // Method to update user cover photo
  updateCoverPhoto(token:string, formData: FormData): Observable<any> {
    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
      // 'Content-Type': 'application/json'
    });
    return this.http.post<any>(`${this.baseUrl}/user/${email}/uploadCoverPhoto`, formData, { headers });
  }

  // Method to update user CV file
  updateCV(token:string, formData: FormData): Observable<any> {
    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
      // 'Content-Type': 'application/json'
    });
    return this.http.post<any>(`${this.baseUrl}/user/${email}/uploadCVFile`, formData, { headers });
  }

  // Method to fetch (download) user profile photo
  downloadProfilePhoto(token: string, email: string): Observable<any> {
    let useremail: string | null = email;
    if (useremail === '') { //if not given email, get the current user's email
      useremail = this.extractEmailFromToken(token);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.baseUrl}/user/${useremail}/downloadProfilePhoto`, { headers, responseType: 'blob' });
  }

  // Method to fetch (download) user cover photo
  downloadCoverPhoto(token: string, email: string): Observable<any> {
    let useremail: string | null = email;
    if (useremail === '') { //if not given email, get the current user's email
      useremail = this.extractEmailFromToken(token);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.baseUrl}/user/${useremail}/downloadCoverPhoto`, { headers, responseType: 'blob' });
  }

  // Method to fetch (download) user CV file
  downloadCV(token: string, email: string): Observable<any> {
    let useremail: string | null = email;
    if (useremail === '') { //if not given email, get the current user's email
      useremail = this.extractEmailFromToken(token);
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.baseUrl}/user/${useremail}/downloadCVFile`, { headers, responseType: 'blob' });
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////////////


  ////////////////////////////////////////// Article methods /////////////////////////////////////////////
  // Method to create a new article
  createArticle(token: string, article: any): Observable<number> {

    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post<number>(`${this.baseUrl}/user/create_article/${email}`, article, { headers });
  }

  // Method to fetch all my articles
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

  // Method to fetch all my contacts' articles
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

  // Method to like an article
  likeArticle(token: string, articleId: number): Observable<boolean> {
    // Extract the email from the token
    const myEmail = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<boolean>(`${this.baseUrl}/user/${myEmail}/like/${articleId}`, { headers });
  }

  // Method to fetch an article by ID
  getArticleById(token: string, articleId: number): Observable<any> {
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(`${this.baseUrl}/article/${articleId}`, { headers });
  }

  // Method to delete an article
  deleteArticle(token: string, articleID: number): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${this.baseUrl}/article/delete/${articleID}`, { headers });
  }

  // Method to comment on an article
  createComment(token: string, comment: any, articleId: number): Observable<boolean> {
    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.post<boolean>(`${this.baseUrl}/user/create_comment?email=${email}&id=${articleId}`, comment, { headers });
  }

  // Method to delete a comment from an article
  deleteComment(token: string, commentId: number): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${this.baseUrl}/comment/delete/${commentId}`, { headers });
  }

  // Method to upload a photo with an article
  updateArticlePhoto(token:string, formData: FormData, articleID: number): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
      // 'Content-Type': 'application/json'
    });
    return this.http.post<any>(`${this.baseUrl}/article/${articleID}/uploadPhoto`, formData, { headers });
  }

  // Method to fetch (download) an article photo
  downloadArticlePhoto(token: string, articleId: number): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.baseUrl}/article/${articleId}/downloadPhoto`, { headers, responseType: 'blob' });
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////////////


  //////////////////////////////////////////// Job methods ///////////////////////////////////////////////
  // Method to create a new job offer
  createJobOffer(token: string, jobOffer: any): Observable<boolean> {
    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post<boolean>(`${this.baseUrl}/user/create_job/${email}`, jobOffer, { headers });
  }

  // Method to fetch all my job offers
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

  // Method to fetch all the jobs i have applied to
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

  // Method to fetch all the job offers from my contacts
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

  // Method to apply to a job
  applyToJob(token: string, jobID: number): Observable<boolean> {
    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
      // 'Content-Type': 'application/json'
    });
    //Send a empty JSON body, as jobApplication doesnot have any data
    return this.http.post<boolean>(`${this.baseUrl}/user/create_jobApp?email=${email}&id=${jobID}`, {}, { headers });
  }

  // Method to cancel a job application
  deleteJobApplication(token: string, jobID: number): Observable<void> {
    const email = this.extractEmailFromToken(token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${this.baseUrl}/application/${email}/delete/${jobID}`, { headers });
  }

  // Method to return the job applications of a specific given job
  getJobApplications(token: string, jobId: number): Observable<any[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any[]>(`${this.baseUrl}/job/${jobId}/jobapplications`, { headers });
  }
  
  // Method to delete a job offer
  deleteJobOffer(token: string, jobID: number): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${this.baseUrl}/job/delete/${jobID}`, { headers });
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////////////


  //////////////////////////////////// Connections/Contacts methods //////////////////////////////////////
  // Method to fetch all my contacts
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

  // Method to delete a contact (disconnect)
  deleteContact(token: string, userEmail: string): Observable<void> {
    // Extract the email from the token
    const myEmail = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    // Append the email to the URL and send the GET request with the Authorization header
    return this.http.get<void>(`${this.baseUrl}/user/DeleteConnection/${myEmail}/${userEmail}`, { headers });
  }

  // Method to return the relationship situation between two users (in text)
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

  // Method to send a new connection request
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

  // Method to reject a received connection request
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

  // Method to accept a received connection request
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

  // Method to delete a sent connection request
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

  // Method to fetch all users requesting me for connection
  getUsersRequestingMe(token: string): Observable<any> {
    // Extract the email from the token
    const myEmail = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(`${this.baseUrl}/user/RequestingMe/${myEmail}`, { headers });
  }

  //Method to fetch all user i have requested for connection
  getMyRequests(token: string): Observable<any> {
    // Extract the email from the token
    const myEmail = this.extractEmailFromToken(token);
    // Set up the headers with the token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(`${this.baseUrl}/user/MyRequests/${myEmail}`, { headers });
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////////////

}
