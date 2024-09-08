package com.Backend_v10.Articles;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class ArticleController {
    private final ArticleRepository repository;

    ArticleController(ArticleRepository repository){
        this.repository = repository;
    }
}
