package org.route.controller;

import jakarta.validation.Valid;
import org.route.model.Brand;
import org.route.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.Binding;
import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;
    public BrandController(BrandService brandService){
        this.brandService=brandService;
    }
    @GetMapping
    public String getBrands(Model model){
        List<Brand> brands=brandService.getAllBrands();
        model.addAttribute("availableBrands",brands);
        return "brands";
    }
    @GetMapping("/new")
    public String showForm(Model model){
        model.addAttribute("brand",new Brand());
        return "brandForm";
    }
    @PostMapping
    public String addBrand(@ModelAttribute("brand") @Valid Brand brand,
                           BindingResult result){
        if(result.hasErrors()){
            return "brandForm";
        }
        if(brandService.isNameTaken(brand.getName())){
            result.rejectValue("name","duplicate","Brand name already exists");
        }
        if(brandService.isSlugTaken(brand.getSlug())){
            result.rejectValue("slug","duplicate","Slug already exists");
        }
        if(result.hasErrors()){
            return "brandForm";
        }
        brandService.saveBrand(brand);
        return "redirect:/brands";
    }

}
