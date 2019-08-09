package com.daisy.myblog.controller;

import com.daisy.myblog.entity.Category;
import com.daisy.myblog.service.CategoryService;
import com.daisy.myblog.util.RespBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteById(@PathVariable String ids) {
        boolean result = categoryService.deleteCategoryByIds(ids);
        if (result) {
            return new RespBean(true,"success", "删除成功!");
        }
        return new RespBean(false,"error", "删除失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RespBean addNewCate(Category category) {
        if (StringUtils.isNotBlank(category.getCateName())){
            int result = categoryService.addCategory(category);
            if (result == 1) {
                return new RespBean(true,"success", "添加成功!");
            }
        }
        return new RespBean(false,"error", "添加失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public RespBean updateCate(Category category) {
        int i = categoryService.updateCategoryById(category);
        if (i == 1) {
            return new RespBean(true,"success", "修改成功!");
        }
        return new RespBean(false,"error", "修改失败!");
    }
}
