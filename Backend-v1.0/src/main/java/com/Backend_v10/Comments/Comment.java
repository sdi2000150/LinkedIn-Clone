package com.Backend_v10.Comments;


import com.Backend_v10.Articles.Article;
import com.Backend_v10.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Comments")
@NoArgsConstructor
public class Comment {


    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentID;

    String Content;
    
    //Many comments to one owner
    @ManyToOne
    @JoinColumn(name = "OwnerID")
    User CommentOwner;

    @ManyToOne
    @JoinColumn(name = "ArticleID")
    Article CommentArticle;


    public Comment(String content, User commentOwner, Article commentArticle) {
         Content = content;
         this.CommentOwner = commentOwner;
         this.CommentArticle = commentArticle;
    }

    public String getContent() {
        return Content;
    }

    public User getCommentOwner() {
        return CommentOwner;
    }

    public Article getCommentArtcile() {
        return  CommentArticle;
    }
    
    public void setContent(String content) {
        Content = content;
    }

    public void setCommentOwner(User commentOwner) {
        CommentOwner = commentOwner;
    }


    public void setCommentArtcile(Article commentArticle) {
        this.CommentArticle =  commentArticle;
    }

    @Override
    public String toString() {
        return "Comment [Content=" + Content + ", CommentOwner=" + CommentOwner + ", CommentArtcile=" + CommentArticle
                + "]";
    }







    
}
