package com.Backend_v10.Articles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Backend_v10.Comments.Comment;
import com.Backend_v10.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Articles")
@NoArgsConstructor
public class Article {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleID;
    String Text;
    byte[] Photo;

    @OneToMany(mappedBy = "commentID")
    List<Comment> ArticleComments;

    // @JoinColumn(name = "UserID")
    // User ArticleOwner; 

    public Article( String text,byte[] photo){
        this.Text = text;
        this.Photo = photo;
        this.ArticleComments = new ArrayList<>();
        //this.ArticleOwner = Owner;
    }

    public Long getArticleID() {
        return articleID;
    }

    public void setArticleID(Long articleID) {
        this.articleID = articleID;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    @Override
    public String toString() {
        return "Article [articleID=" + articleID + ", Text=" + Text + ", Photo=" + Arrays.toString(Photo) + "]";
    }   

    public void AddComment(Comment NewComment){
        this.ArticleComments.add(NewComment);

    }
}
