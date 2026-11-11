package org.route.service;

import org.route.model.Category;
import org.route.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }
    public List<Category> findTobLvlCategories(){
        return categoryRepository.findAll().stream()
                .filter(category -> category.getParentSlug().isBlank())
                .toList();
    }

    public List<Category> findSubCategoriesOfParent(String parentSlug){
        return categoryRepository.findAll().stream()
                .filter(category -> category.getParentSlug().equals(parentSlug))
                .toList();
    }
    public List<Category> getCategoriesToDisplayInParentChildStyle(){
        List<Category>result=new ArrayList<>();

        List<Category>parents=findTobLvlCategories().stream()
                .sorted(Comparator.comparing(Category::getDisplayOrder))
                .toList();
        for(Category parent:parents){
            result.add(parent);

            List<Category>children=findSubCategoriesOfParent(parent.getSlug()).stream()
                    .sorted(Comparator.comparing(Category::getDisplayOrder))
                    .toList();
            result.addAll(children);
        }
        return result;
    }
}
