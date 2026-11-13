package org.route.controller;


import jakarta.validation.Valid;
import org.route.model.Product;
import org.route.service.BrandService;
import org.route.service.CategoryService;
import org.route.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, BrandService brandService, CategoryService categoryService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products =  productService.getAvailableProducts();
        model.addAttribute("availableProducts", products);
        return "products";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("availableBrands",brandService.getAvailableBrands());
        model.addAttribute("subCategories",categoryService.findSubCategories());
        return "productForm";
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("availableBrands",brandService.getAvailableBrands());
            model.addAttribute("subCategories",categoryService.findSubCategories());
            return "productForm";
        }
        if (productService.isProductExist(product.getSku())) {
            result.rejectValue("sku", "400", "sku is already exist");
        }
        if (result.hasErrors()) {
            model.addAttribute("availableBrands",brandService.getAvailableBrands());
            model.addAttribute("subCategories",categoryService.findSubCategories());
            return "productForm";
        }
        productService.saveProduct(product);
        return "redirect:/products";
    }
}
