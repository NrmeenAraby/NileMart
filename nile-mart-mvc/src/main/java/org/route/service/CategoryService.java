package org.route.service;

import org.route.dto.CategoryDisplay;
import org.route.model.Category;
import org.route.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> findSubCategories(){
        return categoryRepository.findAll().stream()
                .filter(category -> !normalizeParentSlug(category.getParentSlug()).isEmpty())
                .toList();
    }

    public List<Category> findTobLvlCategories(){
        return categoryRepository.findAll().stream()
                .filter(category -> normalizeParentSlug(category.getParentSlug()).isEmpty())
                .toList();
    }
    public List<Category> findActiveTobLvlCategories(){
        return findTobLvlCategories().stream()
                .filter(Category::isActive)
                .toList();
    }

    public List<Category> findSubCategoriesOfParent(String parentSlug){
        return categoryRepository.findAll().stream()
                .filter(category -> Objects.equals(normalizeParentSlug(category.getParentSlug()),
                        normalizeParentSlug(parentSlug)))
                .toList();
    }
    public List<CategoryDisplay> getCategoriesToDisplayInParentChildStyle(){
        List<CategoryDisplay>result=new ArrayList<>();

        List<Category>parents=findTobLvlCategories().stream()
                .sorted(Comparator.comparing(Category::getDisplayOrder))
                .toList();
        for(Category parent:parents){
            result.add(new CategoryDisplay(parent.getName(),parent.getSlug(),null,parent.getDisplayOrder(),parent.isActive()));

            List<CategoryDisplay>children=findSubCategoriesOfParent(parent.getSlug()).stream()
                    .sorted(Comparator.comparing(Category::getDisplayOrder))
                    .map(category->new CategoryDisplay(category.getName(),category.getSlug(),parent.getName(),category.getDisplayOrder(),category.isActive()))
                    .toList();
            result.addAll(children);
        }
        return result;
    }
    public boolean isDisplayOrderTaken(String parentSlug,int displayOrder){
        return categoryRepository.findAll().stream()
                .filter(category -> Objects.equals(normalizeParentSlug(category.getParentSlug()),
                        normalizeParentSlug(parentSlug)))
                .anyMatch(category -> category.getDisplayOrder()==displayOrder);
    }
    public boolean isNameTaken(String parentSlug,String name){
        return categoryRepository.findAll().stream()
                .filter(category -> Objects.equals(normalizeParentSlug(category.getParentSlug()),
                        normalizeParentSlug(parentSlug)))
                .anyMatch(category -> category.getName().equalsIgnoreCase(name));
    }
    private String normalizeParentSlug(String parentSlug) {
        return parentSlug == null ? "" : parentSlug;
    }
    public boolean isSlugTaken(String slug){
        return categoryRepository.findBySlug(slug).isPresent();
    }
    public boolean isParentCategory(String slug){
        Optional<Category> category=categoryRepository.findBySlug(slug);
        return category.isPresent()&& normalizeParentSlug(category.get().getParentSlug()).isEmpty();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

}
