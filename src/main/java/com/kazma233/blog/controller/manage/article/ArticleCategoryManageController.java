package com.kazma233.blog.controller.manage.article;

import com.kazma233.blog.entity.category.ArticleCategory;
import com.kazma233.blog.entity.group.AddGroup;
import com.kazma233.blog.entity.group.UpdateGroup;
import com.kazma233.blog.entity.common.BaseResult;
import com.kazma233.blog.service.article.IArticleCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/manages/categories")
public class ArticleCategoryManageController {

    private IArticleCategoryService articleCategoryService;

    @GetMapping
    public BaseResult all() {
        List<ArticleCategory> categories = articleCategoryService.all();

        return BaseResult.success(categories);
    }

    @PostMapping
    public BaseResult addCategory(@Validated(value = {AddGroup.class}) @RequestBody ArticleCategory category) {
        articleCategoryService.insertCategory(category);

        return BaseResult.success();
    }

    @PutMapping
    public BaseResult updateCategory(@Validated(value = {UpdateGroup.class}) @RequestBody ArticleCategory category) {
        articleCategoryService.updateCategory(category);

        return BaseResult.success();
    }

    @DeleteMapping("/{id}")
    public BaseResult deleteCategory(@PathVariable String id) {
        articleCategoryService.deleteCategory(id);

        return BaseResult.success();
    }

}
