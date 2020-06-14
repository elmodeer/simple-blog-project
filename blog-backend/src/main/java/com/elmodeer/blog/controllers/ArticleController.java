package com.elmodeer.blog.controllers;

import com.elmodeer.blog.models.Article;
import com.elmodeer.blog.models.User;
import com.elmodeer.blog.repository.ArticleRepository;
import com.elmodeer.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/username/{userName}")
    public ResponseEntity<Article> findArticleByUserName(@PathVariable String userName) {
        logger.info("Fetching user with following user name :" + userName);
        User author = userRepository.findByUsername(userName)
                        .orElseThrow(() -> new UsernameNotFoundException("No user with the following user name: " + userName));
        logger.info("User Fetched");
        Article article = articleRepository.findByAuthor(author)
                        .orElseThrow(() -> new EntityNotFoundException("No such article was found"));
        return ResponseEntity.ok().body(article);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Article> findArticleById(@PathVariable int id) {
//        logger.info("Fetching user with following user name :" + userName);
//        User author = userRepository.findByUsername(userName)
//                .orElseThrow(() -> new UsernameNotFoundException("No user with the following user name: " + userName));
//        logger.info("User Fetched");
        Article article = articleRepository.findById(new Long(id))
                .orElseThrow(() -> new EntityNotFoundException("No such article was found"));
        return ResponseEntity.ok().body(article);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Article>> findAll(){
        logger.info("Fetching all articles");
        List<Article> articles = articleRepository.findAll();
        logger.info("Fetched " + articles.size() + " articles");
        return ResponseEntity.ok().body(articles);
    }
}
