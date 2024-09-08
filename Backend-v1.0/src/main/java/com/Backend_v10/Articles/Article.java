package com.Backend_v10.Articles;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
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
}
