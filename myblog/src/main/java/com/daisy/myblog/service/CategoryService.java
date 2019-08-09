package com.daisy.myblog.service;

import com.daisy.myblog.entity.Category;
import java.util.List;

public interface CategoryService {

     List<Category> getAllCategories();

     boolean deleteCategoryByIds(String ids);

     int updateCategoryById(Category category);

     int addCategory(Category category);
}
