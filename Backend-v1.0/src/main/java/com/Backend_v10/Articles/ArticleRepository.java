package com.Backend_v10.Articles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long>{
    
    @Transactional
    @Modifying
    @Query(value = "delete from user_likes ul where ul.article_id = ?1",nativeQuery = true)
    void DeleteLikes(@Param("id") Long id);
}
