package org.route.repository.category;

import jakarta.annotation.PostConstruct;
import org.route.model.Brand;
import org.route.model.Category;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class InMemoryCategoryRepository implements CategoryRepository{
    Map<String, Category> categories=new HashMap<>();
    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return Optional.ofNullable(categories.get(slug));
    }

    @Override
    public void save(Category category) {
        categories.put(category.getSlug(),category);
    }
    @PostConstruct
    void loadBrands(){
        categories.put("electronics", Category.builder()
                .name("Electronics")
                .slug("electronics")
                .parentSlug("")
                .displayOrder(1)
                .build());

        categories.put("mobile-phones", Category.builder()
                .name("Mobile Phones")
                .slug("mobile-phones")
                .parentSlug("electronics")
                .displayOrder(1)
                .build());

        categories.put("laptops", Category.builder()
                .name("Laptops")
                .slug("laptops")
                .parentSlug("electronics")
                .displayOrder(2)
                .build());

        categories.put("electronics-accessories", Category.builder()
                .name("Accessories")
                .slug("electronics-accessories")
                .parentSlug("electronics")
                .displayOrder(3)
                .build());

        categories.put("home-kitchen", Category.builder()
                .name("Home & Kitchen")
                .slug("home-kitchen")
                .parentSlug("")
                .displayOrder(2)
                .build());

        categories.put("cookware", Category.builder()
                .name("Cookware")
                .slug("cookware")
                .parentSlug("home-kitchen")
                .displayOrder(1)
                .build());

        categories.put("lighting", Category.builder()
                .name("Lighting")
                .slug("lighting")
                .parentSlug("home-kitchen")
                .displayOrder(2)
                .build());

        // Fashion
        categories.put("fashion", Category.builder()
                .name("Fashion")
                .slug("fashion")
                .parentSlug("")
                .displayOrder(3)
                .active(false)
                .build());

        categories.put("mens-clothing", Category.builder()
                .name("Men's Clothing")
                .slug("mens-clothing")
                .parentSlug("fashion")
                .displayOrder(1)
                .build());
    }
}
