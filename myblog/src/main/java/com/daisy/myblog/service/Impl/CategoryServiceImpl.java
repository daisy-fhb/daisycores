package com.daisy.myblog.service.Impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.daisy.myblog.dao.CategoryMapper;
import com.daisy.myblog.entity.Category;
import com.daisy.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by daisy.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }
    @Override
    public boolean deleteCategoryByIds(String ids) {
        String[] split = ids.split(",");
        int result = categoryMapper.deleteCategoryByIds(split);
        return result == split.length;
    }
    @Override
    public int updateCategoryById(Category category) {
        return categoryMapper.updateCategoryById(category);
    }
    @Override
    public int addCategory(Category category) {
        category.setDate(new Timestamp(System.currentTimeMillis()));
        category.setId(RandomUtil.randomLong(100));
        return categoryMapper.addCategory(category);
    }
}
