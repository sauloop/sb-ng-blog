package com.inverenlace.sbngblog.service;

import com.inverenlace.sbngblog.dto.ArticleDto;
import com.inverenlace.sbngblog.entity.Article;
import com.inverenlace.sbngblog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArticleService {

    public Page<Article> findAllArticles(Pageable articlePageable);

    public Page<Article> findByCategory(Category category, Pageable articlePageable);

    public ArticleDto findArticleById(Long id);

    public ArticleDto saveArticle(ArticleDto articleDto);

    public ArticleDto updateArticle(Long id, ArticleDto articleDto);

    public void deleteArticleById(Long id);

}
