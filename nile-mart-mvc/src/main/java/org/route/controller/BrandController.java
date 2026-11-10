package org.route.controller;

import org.route.model.Brand;
import org.route.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("brand",Brand.builder().build());
        return "brandForm";
    }

}
