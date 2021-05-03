package com.inverenlace.sbngblog.converter;

import com.inverenlace.sbngblog.dto.ArticleDto;
import com.inverenlace.sbngblog.dto.CategoryDto;
import com.inverenlace.sbngblog.entity.Article;
import com.inverenlace.sbngblog.entity.Category;

public class CategoryConverter {

    // Parsing from Category to CategoryDto
    public static CategoryDto categoryToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.id = category.getId();
        categoryDto.name = category.getName();

        return categoryDto;
    }

    // Parsing from CategoryDto to Category
    public static Category categoryDtoToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.id);
        category.setName(categoryDto.name);

        return category;
    }
}
