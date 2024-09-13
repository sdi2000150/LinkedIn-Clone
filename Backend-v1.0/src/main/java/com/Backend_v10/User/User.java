package com.Backend_v10.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.Jobs.Job;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.ManyToMany;
   
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity             // This tells Hibernate to make a table out of this class
@Data               // Lombok annotation to create all the getters, setters, toString methods based on the fields
@AllArgsConstructor // Lombok annotation to create a constructor with all the arguments
@NoArgsConstructor  // Lombok annotation to create a constructor with no arguments
@Table(name = "Users")
@JsonIgnoreProperties({"myJobs", "myJobApplications"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UserID;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_user_id")
    // @JsonManagedReference
    private List<Article> myArticles;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_user_id")
    // @JsonManagedReference
    private List<Article> myComments;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_user_id")
    // @JsonManagedReference
    private List<Job> myJobs;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_application_user_id")
    // @JsonManagedReference
    private List<JobApplication> myJobApplications;

    // @ManyToMany
    // @JoinColumn(name = "ContactID")
    // private List<User> Contacts;

    //rest of the fields (may be private)
    private String username;
    private String name;
    private String password;
    private String lastname;
    private String phone;
    private String email;
    private byte[] photo;
    private LocalDate birthdate;
    private byte[] CVFile;
    private String role;

     //simple constuctor for testing
    public User(String username, String name, String password, String role, String lastname, String email){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.myArticles = new ArrayList<>();
        this.myJobs = new ArrayList<>();
        this.myJobApplications = new ArrayList<>();
        // this.Contacts = new ArrayList<>();
    }

    // Getters, Setters and toString are automaticaly created (in the background) by Lombok

    // Articles methods:
    public void setMyArticles(List<Article> myArticles) {
        this.myArticles = myArticles;
    }

    @Transactional
    public void addArticle(Article NewArticle){
        this.myArticles.add(NewArticle);
    }
    public List<Article> getMyArticles() {
        return myArticles;
    }

    // Jobs methods:
    public void setMyJobs(List<Job> myJobs) {
        this.myJobs = myJobs;
    }
    @Transactional
    public void addJob(Job newJob) {
        this.myJobs.add(newJob);
    }

    public List<Job> getMyJobs() {
        return myJobs;
    }
    
    // JobApplications methods:
    public void addJobApplication(JobApplication jobApplication) {
        this.myJobApplications.add(jobApplication);
        jobApplication.setUser(this);
    }

    // Connections methods:
    
    // @Transactional
    // public void AddContact(User NewContact){
    //     this.Contacts.add(NewContact);
    // }
}
