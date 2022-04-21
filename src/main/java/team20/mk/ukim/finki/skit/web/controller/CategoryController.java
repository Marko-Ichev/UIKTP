package team20.mk.ukim.finki.skit.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import team20.mk.ukim.finki.skit.model.Category;
import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategories(Model model){
        List<Category> allCategories=categoryService.getAllCategories();
        model.addAttribute("categories", allCategories);
        model.addAttribute("bodyContent", "categories");
        return "master-template";
    }

    @GetMapping("/{id}")
    public String getItemsInCategory(@PathVariable Long id, Model model){
        List<Item> items=categoryService.getAllItemsForCategory(id);
        model.addAttribute("products", items);
        model.addAttribute("bodyContent", "items-in-category");
        return "master-template";
    }
}
