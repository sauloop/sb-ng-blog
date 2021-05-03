package com.inverenlace.sbngblog.controller;

import com.inverenlace.sbngblog.dto.ArticleDto;
import com.inverenlace.sbngblog.dto.CategoryDto;
import com.inverenlace.sbngblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAllCategoryDtos() {
        List<CategoryDto> categories = null;
        categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> findCategoryDtoById(@PathVariable Long id) {
        CategoryDto categoryDto = null;
        categoryDto = categoryService.findCategoryById(id);
        return ResponseEntity.ok(categoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> saveCategoryDto(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = null;
        savedCategoryDto = categoryService.saveCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> updateCategoryDto(@RequestBody CategoryDto categoryDto, @PathVariable Long id) {
        CategoryDto updatedCategoryDto = null;
        updatedCategoryDto = categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
