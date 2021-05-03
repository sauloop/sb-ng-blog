package com.inverenlace.sbngblog.service;

import com.inverenlace.sbngblog.dto.ArticleDto;
import com.inverenlace.sbngblog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    public List<CategoryDto> findAllCategories();

    public CategoryDto findCategoryById(Long id);

    public CategoryDto saveCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    public void deleteCategoryById(Long id);
}
