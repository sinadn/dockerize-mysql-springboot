package com.example.dockerTutorial.controller;

import com.example.dockerTutorial.entity.Article;
import com.example.dockerTutorial.service.ArticleServiceImpl;
import com.example.dockerTutorial.tools.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;






    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestParam("model") String jsonObject, @Nullable @RequestParam("file") MultipartFile file) {
        Article response = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formatDateTime = LocalDateTime.now().format(formatter);
            LocalDateTime today = LocalDateTime.parse(formatDateTime, formatter);
            try {
                String fileName = fileStorageService.storeFile(file);
                ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            }catch (Exception e){
                e.getMessage();
            }
            response = objectMapper.readValue(jsonObject, Article.class);
            Article article = new Article(response.getTitle(), response.getDescription(),
                    response.getUrl(), response.getContent(), response.getImage(), today);
            if (response.getId()!=0) {
                article.setId(response.getId());
            }
            else{
                article.setImage(file.getOriginalFilename());
            }
            articleService.createObjects(article);
            return ResponseEntity.ok("article registered successfully!");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/mainArticle/")
    public ResponseEntity<?> mainArticle() {
        return ResponseEntity.ok(articleService.findMainArticle());
    }

    @PostMapping("/findAll/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @PostMapping("/getAll/{pageNumber}")
    public ResponseEntity<?> getAll(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok(articleService.getAll(pageNumber));
    }

    @PostMapping("/article/{id}")
    public ResponseEntity<?> findArticle(@PathVariable("id") int id) {
        return ResponseEntity.ok(articleService.findArticle(id));
    }


    @PostMapping("/getArticleByWords/{name}")
    public ResponseEntity<?> getArticleByWords(@PathVariable("name") String name) {
            return ResponseEntity.ok(articleService.getArticleByWords(name));
    }


    @PostMapping("/getArticleByTitle/{title}")
    public ResponseEntity<?> getArticleByTitle(@PathVariable("title") String title) {
            return ResponseEntity.ok(articleService.getArticleByTitle(title));
    }


    @PostMapping("/getArticleByUrl{url}")
    public ResponseEntity<?> getArticleByUrl(@PathVariable("url") String url) {
            return ResponseEntity.ok(articleService.findArticleByUrl(url));
    }



    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") int id) {
        articleService.deleteObjects(id);
        return ResponseEntity.ok("article removed successfully!");
    }

}
