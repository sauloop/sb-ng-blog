package com.inverenlace.sbngblog.converter;

import com.inverenlace.sbngblog.dto.ArticleDto;
import com.inverenlace.sbngblog.entity.Article;
import com.inverenlace.sbngblog.entity.Category;

import java.time.LocalDate;

public class ArticleConverter {

    // Parsing from Article to ArticleDto
    public static ArticleDto articleToArticleDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.id = article.getId();
        articleDto.title = article.getTitle();
        if (article.getDate() != null) {
            articleDto.date = article.getDate().toString();
        } else {
            LocalDate dateNow = LocalDate.now();
            articleDto.date = dateNow.toString();
        }
        articleDto.content = article.getContent();
        articleDto.link = article.getLink();
        articleDto.category = article.getCategory().getId();

        return articleDto;
    }


    // Parsing from ArticleDto to Article
    public static Article articleDtoToArticle(ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.id);
        article.setTitle(articleDto.title);
        article.setDate(LocalDate.parse(articleDto.date));
        article.setContent(articleDto.content);
        article.setLink(articleDto.link);
        article.setCategory(Category.builder().id(articleDto.category).build());

        return article;
    }
}
