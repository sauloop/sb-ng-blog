package com.inverenlace.sbngblog.repository;

import com.inverenlace.sbngblog.entity.Article;
import com.inverenlace.sbngblog.entity.Category;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByCategory(Category category, Pageable articlePageable);
}
