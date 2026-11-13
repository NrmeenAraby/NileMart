package org.route.service;

import org.route.model.Brand;
import org.route.model.Category;
import org.route.repository.brand.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    public BrandService(BrandRepository brandRepository){
        this.brandRepository=brandRepository;
    }

    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }
    public List<Brand> getAvailableBrands(){
        return brandRepository.findAll().stream()
                .filter(Brand::isActive)
                .toList();
    }
    public boolean isNameTaken(String name){
        return brandRepository.findAll().stream()
                .anyMatch(brand -> brand.getName().equalsIgnoreCase(name));
    }
    public boolean isSlugTaken(String slug){
        return brandRepository.findBySlug(slug).isPresent();
    }

    public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }
    public Map<String,String> getBrandNames(){
        return brandRepository.findAll().stream()
                .collect(Collectors.toMap(Brand::getSlug,Brand::getName));
    }
    public  boolean isBrandExistAndActive(String brandSlug){
        Optional<Brand>brand=brandRepository.findBySlug(brandSlug);
        return brand.isPresent()&&brand.get().isActive();
    }
}
