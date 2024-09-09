package com.Backend_v10.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Backend_v10.Articles.Article;

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
     @JoinColumn(name = "article_id")
     private List<Article> MyArticles;
    
    //rest of the fields (may be private)
    private String username;
    private String name;
    private String password;
    private String lastname;
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
        MyArticles = new ArrayList<>();
    }

    // Getters and Setters are automaticaly created (in the background) by Lombok

    // @Override
    // public String toString() {
    //     return "User [UserID=" + UserID + ", Username=" + Username + ", Name=" + Name + ", Lastname=" + Lastname
    //             + ", Email=" + Email + ", Photo=" + Arrays.toString(Photo) + ", BirthDate=" + BirthDate + ", CVFile="
    //             + Arrays.toString(CVFile) + "]";
    // }

    public void setMyArticles(List<Article> myArticles) {
        MyArticles = myArticles;
    }

    @Transactional
    public void AddArticle(Article NewArticle){
        this.MyArticles.add(NewArticle);
    }
    public List<Article> getMyArticles() {
        return MyArticles;
    }


}
