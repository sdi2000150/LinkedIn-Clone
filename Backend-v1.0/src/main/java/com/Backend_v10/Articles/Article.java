package com.Backend_v10.Articles;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Backend_v10.Comments.Comment;
import com.Backend_v10.User.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Articles")
@NoArgsConstructor
@AllArgsConstructor
@Data
// @JsonIgnoreProperties({"ArticleComments"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "articleID")
public class Article {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleID;
    String Text;
    byte[] Photo;
    LocalDateTime DateTime_of_Creation;

    @OneToMany //(cascade = CascadeType.ALL )
    @ToString.Exclude
    @JoinColumn(name = "article_id")
    // @JsonManagedReference
    List<Comment> ArticleComments;

    @ManyToMany(mappedBy = "likedArticles")
    @ToString.Exclude
    private List<User> likedByUsers;


    // @JoinColumn(name = "UserID")
    // User ArticleOwner; 

    public Article(String text) {
        this.Text = text;
        this.Photo = null;
        this.ArticleComments = new ArrayList<>();
        this.DateTime_of_Creation = LocalDateTime.now();
        this.likedByUsers = new ArrayList<>();
    }
    public Article( String text,byte[] photo){
        this.Text = text;
        this.Photo = photo;
        this.ArticleComments = new ArrayList<>();
        this.DateTime_of_Creation = LocalDateTime.now();
        this.likedByUsers = new ArrayList<>();
        //this.ArticleOwner = Owner;
    }
    
    // OLD VERSION
    // public void AddComment(String CommentContent,User CommentOwner, Article CommentArticle){
    //     Comment NewComment = new Comment(CommentContent,CommentOwner, CommentArticle);
    //     this.ArticleComments.add(NewComment);
    // }

    //NEW WAY
    public void addComment(Comment newComment) {
        this.ArticleComments.add(newComment);
        newComment.setCommentArticle(this);
    }


}
