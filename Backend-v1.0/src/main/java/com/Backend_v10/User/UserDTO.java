package com.Backend_v10.User;

import java.time.LocalDate;
import java.util.List;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Comments.Comment;
import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.Jobs.Job;

import lombok.Data;

// DTO for User, which is used to transfer user data between the backend and the frontend 
@Data
public class UserDTO {
    private Long UserID;
    private String username;
    private String name;
    private String lastname;
    private String password;
    private String phone;
    private String email;
    private LocalDate birthdate;
    private String role;
    private String profilePhotoUrl;
    private String coverPhotoUrl;
    private String cvFileUrl;
    private String about;
    private String experience;
    private String experienceDescription;
    private String education;
    private String educationDescription;
    private String skills;
    private List<Article> myArticles;
    private List<Comment> myComments;
    private List<Job> myJobs;
    private List<JobApplication> myJobApplications;
    private List<User> myContacts;
    private List<Article> likedArticles;
}