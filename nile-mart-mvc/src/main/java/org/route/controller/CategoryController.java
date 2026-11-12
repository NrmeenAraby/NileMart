package org.route.controller;

import jakarta.validation.Valid;
import org.route.model.Category;
import org.route.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategories(Model model){
        model.addAttribute("categories",categoryService.getCategoriesToDisplayInParentChildStyle());
        return "categories";
    }
    @GetMapping("/new")
    public String showForm(Model model){
        model.addAttribute("category",new Category());
        model.addAttribute("parentCategories",categoryService.findActiveTobLvlCategories());
        return "categoryForm";
    }
    @PostMapping
    public String addCategory(@ModelAttribute("category")@Valid Category category,
                              BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute("parentCategories",categoryService.findActiveTobLvlCategories());
            return "categoryForm";
        }
        String parentSlug = category.getParentSlug() == null ? "" : category.getParentSlug();
        if(categoryService.isDisplayOrderTaken(parentSlug,category.getDisplayOrder())){
            result.rejectValue("displayOrder","duplicate","This display order is already taken.");
        }
        if(categoryService.isNameTaken(parentSlug,category.getName())){
            result.rejectValue("name","duplicate","This name is already taken.");
        }
        if(!parentSlug.isEmpty() && !categoryService.isParentCategory(parentSlug)){
            result.rejectValue("parentSlug","invalid","Parent must be an existing top-level category.");
        }
        if(categoryService.isSlugTaken(category.getSlug())){
            result.rejectValue("slug","duplicate","This slug is already taken.");
        }
        if(result.hasErrors()){
            model.addAttribute("parentCategories",categoryService.findActiveTobLvlCategories());
            return "categoryForm";
        }
        category.setParentSlug(parentSlug);
        categoryService.save(category);
        return "redirect:/categories";
    }
}
