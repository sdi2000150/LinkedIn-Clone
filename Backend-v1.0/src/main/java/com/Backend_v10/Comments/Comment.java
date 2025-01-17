package com.Backend_v10.Comments;


import com.Backend_v10.Articles.Article;
import com.Backend_v10.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "Comments")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "commentID")
public class Comment {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentID;
    String Content;
    //Many comments to one owner
    @ManyToOne
    @JoinColumn(name = "owner_ID")
    User CommentOwner;
    @ManyToOne
    @JoinColumn(name = "article_ID")
    Article CommentArticle;

    @Override
    public String toString() {
        return "Comment [commentID=" + commentID + ", Content=" + Content + ", CommentOwner=" + CommentOwner
                + ", CommentArticle=" + CommentArticle + "]";
    }

    
}
