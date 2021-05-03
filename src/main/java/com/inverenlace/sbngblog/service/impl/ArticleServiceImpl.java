package com.inverenlace.sbngblog.service.impl;

import com.inverenlace.sbngblog.converter.ArticleConverter;
import com.inverenlace.sbngblog.dto.ArticleDto;
import com.inverenlace.sbngblog.dto.Mensaje;
import com.inverenlace.sbngblog.entity.Article;
import com.inverenlace.sbngblog.entity.Category;
import com.inverenlace.sbngblog.exception.BadRequestException;
import com.inverenlace.sbngblog.exception.NotFoundException;
import com.inverenlace.sbngblog.repository.ArticleRepository;
import com.inverenlace.sbngblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Page<Article> findAllArticles(Pageable articlePageable) {
        return articleRepository.findAll(articlePageable);
    }

    @Override
    public Page<Article> findByCategory(Category category, Pageable articlePageable) {
        return articleRepository.findByCategory(category, articlePageable);
    }

    @Override
    public ArticleDto findArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new NotFoundException("El artículo con id: " + id + " no existe en la base de datos."));
        ArticleDto articleDto = ArticleConverter.articleToArticleDto(article);
        return articleDto;
    }

    @Override
    public ArticleDto saveArticle(ArticleDto articleDto) {
        ArticleDto savedArticleDto = null;
        try {
            Article article = articleRepository.save(ArticleConverter.articleDtoToArticle(articleDto));
            savedArticleDto = ArticleConverter.articleToArticleDto(article);
        } catch (Exception e) {
            throw new BadRequestException("Los datos del artículo a guardar no son correctos.");
        }
        return savedArticleDto;
    }

    @Override
    public ArticleDto updateArticle(Long id, ArticleDto articleDto) {
        ArticleDto updatedArticleDto = null;
        ArticleDto currentArticleDto = this.findArticleById(id);
        try {
            if (currentArticleDto != null) {
                currentArticleDto.title = articleDto.title;
                currentArticleDto.content = articleDto.content;
                currentArticleDto.link = articleDto.link;
                currentArticleDto.category = articleDto.category;

                updatedArticleDto = this.saveArticle(currentArticleDto);
            }
        } catch (Exception e) {
            throw new BadRequestException("Los datos del artículo a actualizar no son correctos.");
        }
        return updatedArticleDto;
    }

    @Override
    public void deleteArticleById(Long id) {
        try {
            articleRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("El artículo con id: " + id + " no existe en la base de datos.");
        }
    }

    //    @Override
//    public void saveArticle(ArticleDto articleDto) {
//        try {
//            articleRepository.save(ArticleConverter.articleDtoToArticle(articleDto));
//        } catch (Exception e) {
//            throw new BadRequestException("Los datos del artículo a guardar no son correctos.");
//        }
//    }
}
