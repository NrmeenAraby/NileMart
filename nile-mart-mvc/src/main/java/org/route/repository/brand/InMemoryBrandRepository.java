package org.route.repository.brand;

import jakarta.annotation.PostConstruct;
import org.route.model.Brand;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryBrandRepository implements BrandRepository{
    Map<String,Brand> brands=new HashMap<>();
    @Override
    public List<Brand> findAll() {
        return new ArrayList<>(brands.values());
    }

    @Override
    public Optional<Brand> findBySlug(String slug) {
        return Optional.ofNullable(brands.get(slug));
    }

    @Override
    public void save(Brand brand) {
        brands.put(brand.getSlug(),brand);
    }
    @PostConstruct
    void loadBrands(){
        brands.put("samsung",Brand.builder()
                .name("Samsung")
                .slug("samsung")
                .build());

        brands.put("asus", Brand.builder()
                .name("Asus")
                .slug("asus")
                .build());

        brands.put("anker", Brand.builder()
                .name("Anker")
                .slug("anker")
                .build());

        brands.put("tefal", Brand.builder()
                .name("Tefal")
                .slug("tefal")
                .build());

        brands.put("old-brand", Brand.builder()
                .name("Old Brand")
                .slug("old-brand")
                .active(false)
                .build());
    }
}
