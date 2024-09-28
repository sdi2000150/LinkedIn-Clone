package com.Backend_v10.Articles;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Backend_v10.Comments.Comment;
import com.Backend_v10.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "articleID")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleID;
    String Text;
    @Lob    
    String PhotoUrl;
    LocalDateTime DateTime_of_Creation;
    @ManyToOne
    @JoinColumn(name = "article_user_id",referencedColumnName = "UserID")
    User Owner;
    @OneToMany //(cascade = CascadeType.ALL )
    @ToString.Exclude
    @JoinColumn(name = "article_id")
    List<Comment> ArticleComments;
    @ManyToMany(mappedBy = "likedArticles")
    @ToString.Exclude
    private List<User> likedByUsers;

    public Article(String text) {
        this.Text = text;
        this.PhotoUrl = null;
        this.ArticleComments = new ArrayList<>();
        this.DateTime_of_Creation = LocalDateTime.now();
        this.likedByUsers = new ArrayList<>();
    }
    public Article( String text,String photo){
        this.Text = text;
        this.PhotoUrl = photo;
        this.ArticleComments = new ArrayList<>();
        this.DateTime_of_Creation = LocalDateTime.now();
        this.likedByUsers = new ArrayList<>();
    }
        public void addComment(Comment newComment) {
        this.ArticleComments.add(newComment);
        newComment.setCommentArticle(this);
    }
    @Override
    public String toString() {
        return "Article [articleID=" + articleID + ", Text=" + Text + ", Photo=" + PhotoUrl
                + ", DateTime_of_Creation=" + DateTime_of_Creation + ", Owner=" + Owner + "]";
    }


}
