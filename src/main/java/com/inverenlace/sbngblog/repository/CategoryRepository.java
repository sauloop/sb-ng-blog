package com.inverenlace.sbngblog.repository;

import com.inverenlace.sbngblog.entity.Category;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
