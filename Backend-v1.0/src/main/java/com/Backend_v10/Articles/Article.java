package com.Backend_v10.Articles;

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
}
