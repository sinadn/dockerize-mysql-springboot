package com.example.dockerTutorial.service;

import com.example.dockerTutorial.entity.Article;
import com.example.dockerTutorial.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl {
    @Autowired
    ArticleRepository articleRepository;

    public void createObjects(Article article) {
        articleRepository.save(article);
    }

    public Page<Article> findAll() {
        return articleRepository.findAll(PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "title")));
    }


    public List<Article> getArticleByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  articleRepository.getArticleByWords(name , paging);
    }


    public Article getArticleByTitle(String name) {
        return  articleRepository.findArticleByTitle(name);
    }

    public Article findArticleByUrl(String name) {
        return  articleRepository.findArticleByUrl(name);
    }


    public Page<Article> findMainArticle() {
        return  articleRepository.findAll(PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "title")));
    }

    public Page<Article> getAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return  articleRepository.findAll(paging);
    }


    public Article findArticle(int id) {
        return articleRepository.findByArticleId(id);
    }
    public void deleteObjects(int id) {
        articleRepository.deleteById(id);
    }





}
