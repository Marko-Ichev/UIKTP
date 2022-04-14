package team20.mk.ukim.finki.skit.web.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team20.mk.ukim.finki.skit.model.Category;
import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.Subject;
import team20.mk.ukim.finki.skit.service.CategoryService;
import team20.mk.ukim.finki.skit.service.ItemService;
import team20.mk.ukim.finki.skit.service.SubjectService;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {


    private final ItemService productService;
    private final CategoryService categoryService;
    private final SubjectService subjectService;

    public ItemController(ItemService productService, CategoryService categoryService, SubjectService subjectService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.subjectService = subjectService;
    }


    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Item> products = this.productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "items");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.productService.findById(id).isPresent()) {
            Item product = this.productService.findById(id).get();
            List<Subject> subjects = this.subjectService.getAllSubjects();
            model.addAttribute("subjects", subjects);
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "add-product");
            return "master-template";
        }
        return "redirect:/products?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model) {
        List<Subject> subjects = this.subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);


        model.addAttribute("bodyContent", "add-product");
        return "master-template";
    }

//    @PostMapping("/add")
//    public String saveProduct(
//            @RequestParam(required = false) Long id,
//            @RequestParam String name,
//            @RequestParam Double price,
//            @RequestParam Integer quantity,
//            @RequestParam Long category,
//            @RequestParam Long manufacturer) {
//        if (id != null) {
//            this.productService.edit(id, name, price, quantity, category, manufacturer);
//        } else {
//            this.productService.save(name, price, quantity, category, manufacturer);
//        }
//        return "redirect:/products";
//    }

}
