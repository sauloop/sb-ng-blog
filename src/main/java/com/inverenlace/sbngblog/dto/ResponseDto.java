package com.inverenlace.sbngblog.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

// @JsonInclude annotation ensures that only non null values are sent in the response.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public final class ResponseDto {

    private final List<ArticleDto> articles;
    private final int totalItems;
    private final int totalPages;
    private final int currentPage;
    private final Boolean isFirstPage;
    private final Boolean isLastPage;

    private ResponseDto(final List<ArticleDto> articles, final int totalItems, final int totalPages, final int currentPage,
                        final Boolean isFirstPage, final Boolean isLastPage) {
        this.articles = articles;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.isFirstPage = isFirstPage;
        this.isLastPage = isLastPage;
    }

    public static ResponseDto create(final List<ArticleDto> articles, final int totalItems, final int totalPages,
                                     final int currentPage, final Boolean isFirstPage, final Boolean isLastPage) {
        return new ResponseDto(articles, totalItems, totalPages, currentPage, isFirstPage, isLastPage);
    }

    public List<ArticleDto> getArticles() {
        return articles;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public Boolean getIsFirstPage() {
        return isFirstPage;
    }

    public Boolean getIsLastPage() {
        return isLastPage;
    }
}
