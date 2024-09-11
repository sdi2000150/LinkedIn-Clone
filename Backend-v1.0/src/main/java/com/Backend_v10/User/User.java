package com.Backend_v10.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Backend_v10.Articles.Article;
import com.Backend_v10.Jobs.Job;
import jakarta.persistence.ManyToMany;
   
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;




@Entity             // This tells Hibernate to make a table out of this class
@Data               // Lombok annotation to create all the getters, setters, toString methods based on the fields
@AllArgsConstructor // Lombok annotation to create a constructor with all the arguments
@NoArgsConstructor  // Lombok annotation to create a constructor with no arguments
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UserID;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_user_id")
    private List<Article> myArticles;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_user_id")
    private List<Job> myJobs;

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

    public User(String username, String name, String password, String role, String lastname, String email){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.myArticles = new ArrayList<>();
        this.myJobs = new ArrayList<>();
        // this.Contacts = new ArrayList<>();
    }

    // Getters and Setters are automaticaly created (in the background) by Lombok

    // @Override
    // public String toString() {
    //     return "User [UserID=" + UserID + ", Username=" + Username + ", Name=" + Name + ", Lastname=" + Lastname
    //             + ", Email=" + Email + ", Photo=" + Arrays.toString(Photo) + ", BirthDate=" + BirthDate + ", CVFile="
    //             + Arrays.toString(CVFile) + "]";
    // }

    public void setMyArticles(List<Article> myArticles) {
        myArticles = myArticles;
    }

    @Transactional
    public void AddArticle(Article NewArticle){
        this.myArticles.add(NewArticle);
    }
    public List<Article> getMyArticles() {
        return myArticles;
    }


    // @Transactional
    // public void AddContact(User NewContact){
    //     this.Contacts.add(NewContact);
    // }


    public void AddJob(Job NewJob){
        this.myJobs.add(NewJob);
    }   
}
