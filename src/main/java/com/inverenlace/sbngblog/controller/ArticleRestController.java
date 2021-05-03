package com.inverenlace.sbngblog.controller;

import com.inverenlace.sbngblog.dto.ArticleDto;
import com.inverenlace.sbngblog.entity.Article;
import com.inverenlace.sbngblog.entity.Category;
import com.inverenlace.sbngblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResponseEntity<Page<Article>> findAllArticles(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(name = "categoryId", required = false) Long categoryId) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by("id").descending());
        Page<Article> articles = articleService.findAllArticles(pageable);
        if (categoryId == null) {
            if (articles.isEmpty()) {
//                return ResponseEntity.noContent().build();
                return ResponseEntity.ok(articles);
            }
        } else {
            pageable = PageRequest.of(page, 2, Sort.by("id").descending());
            articles = articleService.findByCategory(Category.builder().id(categoryId).build(), pageable);
            if (articles.isEmpty()) {
//                return ResponseEntity.notFound().build();
                return ResponseEntity.ok(articles);
            }
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticleDto> findArticleDtoById(@PathVariable Long id) {
        ArticleDto articleDto = null;
        articleDto = articleService.findArticleById(id);
        return ResponseEntity.ok(articleDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> saveArticleDto(@RequestBody ArticleDto articleDto) {
        ArticleDto savedArticleDto = null;
        savedArticleDto = articleService.saveArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticleDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> updateArticleDto(@PathVariable Long id, @RequestBody ArticleDto articleDto) {
        ArticleDto updatedArticleDto = null;
        updatedArticleDto = articleService.updateArticle(id, articleDto);
        return ResponseEntity.ok(updatedArticleDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id) {
        articleService.deleteArticleById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
