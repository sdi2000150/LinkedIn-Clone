package com.Backend_v10.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Comments.Comment;
import com.Backend_v10.JobApplication.JobApplication;
import com.Backend_v10.Jobs.Job;
import com.Backend_v10.UserConnection.UserConnection;
import com.Backend_v10.UserConnection.UserConnectionRepository;
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
import jakarta.persistence.UniqueConstraint;
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
@JsonIgnoreProperties({"myArticles", "myComments", "myJobs", "myJobApplications", "myContacts"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UserID;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_user_id")
    // @JsonManagedReference
    private List<Article> myArticles;

    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name = "article_user_id")
    // @JsonManagedReference
    private List<Comment> myComments;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_user_id")
    // @JsonManagedReference
    private List<Job> myJobs;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_application_user_id")
    // @JsonManagedReference
    private List<JobApplication> myJobApplications;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //these cascades for the auto save/addContact of user2, when saving user1
    @JoinTable(
        name = "Contacts",
        joinColumns = @JoinColumn(name = "user1"),
        inverseJoinColumns = @JoinColumn(name = "user2")
    )
    private List<User> myContacts;

    // Rest of the fields (may be private)
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

     // Simple constructor for testing
    public User(String username, String name, String password, String role, String lastname, String email){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.myArticles = new ArrayList<>();
        this.myComments = new ArrayList<>();
        this.myJobs = new ArrayList<>();
        this.myJobApplications = new ArrayList<>();
        this.myContacts = new ArrayList<>();
        // this.connectionsInitiated = new ArrayList<>();
        // this.connectionsReceived = new ArrayList<>();
        // this.Contacts = new ArrayList<>();
    }

    // Getters, Setters and toString are automaticaly created (in the background) by Lombok

    // Articles methods:
    @Transactional
    public void addArticle(Article newArticle){
        this.myArticles.add(newArticle);
    }

    // Comments methods:
    @Transactional
    public void addComment(Comment newComment){
        this.myComments.add(newComment);
        newComment.setCommentOwner(this);
    }

    // Jobs methods:
    @Transactional
    public void addJob(Job newJob) {
        this.myJobs.add(newJob);
    }
    
    // JobApplications methods:
    @Transactional
    public void addJobApplication(JobApplication newjobApplication) {
        this.myJobApplications.add(newjobApplication);
        newjobApplication.setUser(this);
    }

    // Contacts methods:
    @Transactional
    public void addContact(User user) {
        if (!this.myContacts.contains(user)) {
            this.myContacts.add(user);
            if (!user.getMyContacts().contains(this)) {
                user.getMyContacts().add(this);
            }
        }
    }
}
