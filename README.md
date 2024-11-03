# ![Favicon](https://raw.githubusercontent.com/sdi2000150/LinkedIn-Clone/refs/heads/main/Frontend-v1.0/src/assets/icons/favicon.ico) # LinkedIn-Clone
Mock LinkedIn-Clone Web Application - Project for Uni Course<br>
Original Repository: https://github.com/NikitasMosch/LinkedInApp

## The Team
- [Nikitas Moschos (sdi2000135)](https://github.com/NikitasMosch)  
- [Theodoros Moraitis (sdi2000150)](https://github.com/sdi2000150)

## Project description
This Project serves as a LinkedIn Web App Clone. Meaning that supports some of the basic features of the actual LinkedIn Web Application.
Most of the features and services requested were delived. One of the major exclusions were the conversation between users, some notifications and 
the support for video or audio input.

## Some technical details
- The frameworks used were Angular (v18.1.0) for the Front-end part, and SpringBoot (v3.3.3) for the Back-end part, paired with MySQL Database.
- In order to run, Angular-folder as well as SpringBoot-folder must be downloaded. Inside the Angular project folder the `ng serve --ssl --ssl-cert src/ssl/cert.pem --ssl-key src/ssl/key.pem` command must be run in the terminal. For the server to start running, inside the SpringBoot project folder, the Application.java file must be executed. NOTE: apppication.properties must be configured correctly (MySQL Database configuration).
- SSL/TLS is added, so every request is encrypted. This was achieved by generating and using a self-signed certificate. Both back-end and front-end use the same certificate. For that reason the App's front-end URL is https://localhost:4200, which communicates with back-end at https://localhost:8443. NOTE: due to the self-signed certificate, the browser may display a security warning that you will need to bypass by selecting the option to continue.
- JSON Web Token (JWT) is used for the authentication of the users. When a user logs in, a unique JWT is generated, and every other request by the user includes the JWT in the headers. An unauthenticated user only has access to the Welcome-Page, Login-Page and Signup-Page. No refresh token is added, so the JWT is set to be expired after 30 minutes on its generation, and then the user is redirected to the Login-Page again.
## The app:
### Welcome-Page
It is the initial page when loading https://localhost:4200 and it welcomes the user, giving the option to login or signup:
<br><br>
![Welcome-Page Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/welcome-page.png)

### Login-Page
The user logs in as a result of him correctly submitting his email and password, and being authorized and authenticated. If the credentials belong to an admin, Admin-Page is loaded, otherwise User-Page is loaded:
<br><br>
![Login-Page Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/login-page.png)

### Signup-Page
When the user signs up, he can submit all wanted information, except for images (profile and cover photos) that can be only inserted in the profile section of the app afterwards:
<br><br>
![Signup-Page Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/signup-page.png)

### User-Page:
#### Home
In the home page, user can post a new article. In the main section recommended articles are displayed (based on bonus: recommendation system), which can be liked or commented by the user. On the right-side there is the option to redirect to the articles the user has posted, or to the articles the user's contacts have posted:
<br>
![User-Home Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/user-home.png)

#### Network
All Contacts of the User are displayed as a grid. There is also a search area available, for searching other Users and accessing their profile in read-only format:
<br>
![Network Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/network.png)

Throught the other user's profile-view, a connection request can be sent, canceled, accepted, declined, or disconnect with the specific user:
<br>
![Profile-View Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/profile-view.png)

#### Jobs
In this page job-offers are displayed. On the left-sidebar are shown the jobs the current user offers. On the right-sidebar are shown the jobs the current user has applied to. On the middle section recommended jobs are shown (based on bonus: recommendation system).There is also a section available, for uploading a new job-offer:
<br><br>
![Jobs Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/jobs.png)

#### Notifications
Notification were implemented only as a display of the requests submited from and to the user. No notifications are sent during the liking/commenting of 
the user's posts or due to applications on his jobs:
<br><br>
![Notifications Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/notifications.png)

#### Profile
The profile section shows all User information. Here, user can add: About info, CV, Education, Experience, Skills. There is no feature separating private/public information from other users:
<br><br>
![Profile Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/profile.png)

#### Settings
Here the user can change his password and email. In order to do it, he must submit his old password as a security measure. His new 
email must not exist in the database:
<br><br>
![Settings Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/settings.png)

### Admin-Page:
#### Home
The home page of the Admin, a description of his capabilities is displayed, and the option to redirect to managing all users:
<br><br>
![Admin-Home Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/admin-home.png)

#### Users-List
Here, the admin can see a list of all users who are currently singed up in the app. He has the option to download each user's data in JSON format, or navigate to each user's profile-view page:
<br><br>
![Admin-UsersList Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/admin-userslist.png)

#### User-View
Each user's profile-view page displays the user's profile, in a read-only format:
<br><br>
![Admin-UserView Screenshot](https://github.com/NikitasMosch/LinkedInApp/blob/main/Screenshots/admin-userview.png)



### Bonus Task: Recommendation System

A recommendation system was implemented for both articles and jobs. The system suggests articles and jobs to the user, depending on his previous interaction with other articles/jobs. IMPORTANT NOTE: a like is considered an interaction with an article, while for the jobs the same applies for job applications!!! 
The algorithm implements matrix factorization meaning that in the beggining we have the user/articles and user/jobs interaction matrix. The model is trained and then comes up with 2 submatrices P,Q whose dot product results to the prediction matrix where scores for each article/job per user is stored. Then all articles/jobs are sorted and recommended to the user starting from the higher score. That means, that the submatrices were not calculated by user input or database information, but rather the training of the model calculates P,Q using blunt mathematics and minimizing the loss function. This is considered the Model based approach.
NOTE: The recommendation system is not correctly fined tuned and trained (deduction made by the discouraging results). This phenomemon could be the result of poorly created dataset as well as wrong selection of hyperparameters. Suggestions to make the model perform better would be appreciated.

<br>

**_More detailed information about the project is provided in the Final-Report.pdf (in greek)_**
