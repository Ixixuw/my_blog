package com.example.blog.service;

import com.example.blog.dao.pojo.Category;
import com.example.blog.vo.Result;

public interface CategoryService {
    Category findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoriesDetailById(Long id);
}
