# LinkedIn-Clone
Mock LinkedIn-Clone Web Application - Project for Uni Course


Δουλεουμε σε Angular για Front-End (Javascript/Typescript)<br>
και SpringBoot για Back-End (Java)

## Project description
This Project serves as a LinkedIn App Clone. Meaning that supports some of the basic features of the actual LinkedIn Application.
Most of the features and services requested, were delived. One the major exclusions were the conversation between users, some notifications and 
the support for video or audio input.

## Some technical details
The frameworks used were Spring boot for the Backend part, Angular for the Front end part paired with Mysql Database.
In order to run, angular must be downloaded. Inside the project folder ng serve command must be run in the terminal. For the server to start running,
the application.java file must be executed. NOTE: apppication.properties must be configured correctly.
## The app:
### Welcome-Page

### Login-Page
The user logs in as a result of him correctly submitting his email and password. In other case, error message is displayed.
### Signup-Page
When the user signs up, he can submit all wanted information, except for images (profile and cover photos) that can be only inserted in the profile section of the app

### User-Page:
#### Home

#### Network
All Contacts of the User are displayed as a grid. 
#### Jobs

#### Notifications
Notification were implemented only as a display of the request submited from and to the user. No notifications are sent during the liking of 
the users posts or applications on his jobs.
#### Profile
The profile section shows all information. There is no feature separating private/public information from other users.
#### Settings
Here the user can change his password and email. In order to do it, he must submit his old password as a security measure. His new 
email must not exist in the database. This is handled by the app.
### Admin-Page:
#### Home

#### Users-List

#### User-View

#### BONUS TASK: RECOMMENDATION SYSTEM

A recommendation system was implemented for both articles and jobs. The system suggests articles and jobs to the user, depending on his previous interaction with other articles/jobs. IMPORTANT NOTE: a like is considered an interaction with an article, while for the jobs the same applies for job applications!!! 
The algorithm implements matrix factorization meaning the in the beggining we have the user/articles and user/jobs interaction matrix. The model is trained and then comes up with 2 submatrices P,Q whose dot product results to the prediction matrix where scores for each article/job per user is stored. Then all articles/jobs are sorted and recommended to the user starting from the higher score. That means, that the submatrices were not calculated by user input or database information, but rather the training of the model calculates P,Q using blunt mathematics and minimizing the loss function. This is considered the Model based approach.
NOTE: The recommendation system is not correctly fined tuned and trained (deduction made by the discouraging results). This phenomemon could be the result of poorly created dataset as well as wrong selection of hyperparameters. Suggestions to make the model perform better would be appreciated.


#### More Notes

1) Images and Files are not stored in the database but rather stored locally in the server. The database stores the path to each file and matches them to their owners. The name giving of the files is handled by the app.

2) Admin can only download users personal information in json form. Not XML.

3)

