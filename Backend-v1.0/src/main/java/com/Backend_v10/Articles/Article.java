package com.Backend_v10.Articles;

import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Article(String text,byte[] photo){
        this.Text = text;
        this.Photo = photo;
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

}
