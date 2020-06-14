package com.elmodeer.blog.repository;

import com.elmodeer.blog.models.Article;
import com.elmodeer.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByAuthor(User author);
    Optional<Article> findById(Long id);
    List<Article> findAll();
}
