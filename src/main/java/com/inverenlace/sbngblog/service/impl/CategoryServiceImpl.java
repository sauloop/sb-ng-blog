package com.inverenlace.sbngblog.service.impl;

import com.inverenlace.sbngblog.converter.ArticleConverter;
import com.inverenlace.sbngblog.converter.CategoryConverter;
import com.inverenlace.sbngblog.dto.ArticleDto;
import com.inverenlace.sbngblog.dto.CategoryDto;
import com.inverenlace.sbngblog.entity.Article;
import com.inverenlace.sbngblog.entity.Category;
import com.inverenlace.sbngblog.exception.BadRequestException;
import com.inverenlace.sbngblog.exception.NotFoundException;
import com.inverenlace.sbngblog.repository.CategoryRepository;
import com.inverenlace.sbngblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoriesDto = categories.stream().map(category -> CategoryConverter.categoryToCategoryDto(category))
                .collect(Collectors.toList());
        return categoriesDto;
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("La categoría con id: " + id + " no existe en la base de datos."));
        CategoryDto categoryDto = CategoryConverter.categoryToCategoryDto(category);
        return categoryDto;
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = null;
        try {
            Category category = categoryRepository.save(CategoryConverter.categoryDtoToCategory(categoryDto));
            savedCategoryDto = CategoryConverter.categoryToCategoryDto(category);
        } catch (Exception e) {
            throw new BadRequestException("Los datos de la categoría a guardar no son correctos.");
        }
        return savedCategoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        CategoryDto updatedCategoryDto = null;
        CategoryDto currentCategoryDto = this.findCategoryById(id);
        try {
            if (currentCategoryDto != null) {
                currentCategoryDto.name = categoryDto.name;

                updatedCategoryDto = this.saveCategory(currentCategoryDto);
            }
        } catch (Exception e) {
            throw new BadRequestException("Los datos de la categoría a actualizar no son correctos.");
        }
        return updatedCategoryDto;
    }

    @Override
    public void deleteCategoryById(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("La categoría con id: " + id + " no existe en la base de datos.");
        }
    }
}
