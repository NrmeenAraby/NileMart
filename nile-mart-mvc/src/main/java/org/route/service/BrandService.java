package org.route.service;

import org.route.model.Brand;
import org.route.repository.brand.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<Brand> availableBrands=new ArrayList<>();
        for(Brand brand:brandRepository.findAll()){
            if(brand.isActive()) {
                availableBrands.add(brand);
            }
        }
        return availableBrands;
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
}
