package com.inverenlace.sbngblog.controller;

import com.inverenlace.sbngblog.converter.ArticleConverter;
import com.inverenlace.sbngblog.dto.ArticleDto;
import com.inverenlace.sbngblog.dto.ResponseDto;
import com.inverenlace.sbngblog.entity.Article;
import com.inverenlace.sbngblog.entity.Category;
import com.inverenlace.sbngblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleRestController {

    @Autowired
    private ArticleService articleService;

    @Value("${pagesize}")
    private int pagesize;


    @GetMapping
    public ResponseEntity<ResponseDto> findAllArticles(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(name = "categoryId", required = false) Long categoryId) {

        final ResponseEntity<ResponseDto> responseEntity;

        try {
            Pageable pageable = PageRequest.of(page, this.pagesize, Sort.by("id").descending());
            Page<Article> articles = articleService.findAllArticles(pageable);

            if (categoryId != null) {
                articles = articleService.findByCategory(Category.builder().id(categoryId).build(), pageable);
            }

            responseEntity = createResponseDto(articles);

        } catch (final Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
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

    private ResponseEntity<ResponseDto> createResponseDto(final Page<Article> articlesPage) {
        final List<ArticleDto> articlesDto = articlesPage.getContent().stream().map(article -> ArticleConverter.articleToArticleDto(article))
                .collect(Collectors.toList());
        final ResponseEntity<ResponseDto> responseEntity;
        if (CollectionUtils.isEmpty(articlesDto)) {
            responseEntity = new ResponseEntity<>(ResponseDto.create(Collections.emptyList(), 0, 0, 0, null, null),
                    HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(ResponseDto.create(articlesDto, (int) articlesPage.getTotalElements(),
                    articlesPage.getTotalPages(), articlesPage.getNumber(), articlesPage.isFirst(),
                    articlesPage.isLast()), HttpStatus.OK);
        }
        return responseEntity;
    }
}
