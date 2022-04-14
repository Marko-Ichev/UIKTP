package team20.mk.ukim.finki.skit.web.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team20.mk.ukim.finki.skit.model.Category;
import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.Subject;
import team20.mk.ukim.finki.skit.model.User;
import team20.mk.ukim.finki.skit.service.CategoryService;
import team20.mk.ukim.finki.skit.service.ItemService;
import team20.mk.ukim.finki.skit.service.SubjectService;
import team20.mk.ukim.finki.skit.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {


    private final ItemService productService;
    private final CategoryService categoryService;
    private final SubjectService subjectService;
    private final UserService userService;

    public ItemController(ItemService productService, CategoryService categoryService, SubjectService subjectService, UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.subjectService = subjectService;
        this.userService = userService;
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
        return "redirect:/items/getItemsForUser";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.productService.findById(id).isPresent()) {
            Item product = this.productService.findById(id).get();
            List<Subject> subjects = this.subjectService.getAllSubjects();
            List<Category> categories=this.categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("subjects", subjects);
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "add-product");
            return "master-template";
        }
        return "redirect:/items?error=ProductNotFound";
    }

    @GetMapping("/add")
    public String addProductPage(Model model) {
        List<Subject> subjects = this.subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        List<Category> categories=this.categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-product");
        return "master-template";
    }

    @GetMapping("/getItemsForUser")
    public String getItemsForUser(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        User user = (User) userService.loadUserByUsername(username);
        model.addAttribute("products",user.getItemsForSelling());
        model.addAttribute("bodyContent", "items-from-user");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(@RequestParam String name,
                              @RequestParam(required = false) Long prodId,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam Long category,
                              @RequestParam Long subject, HttpServletRequest request){
        productService.save(name,prodId,price,quantity,category,subject,request.getRemoteUser());
        return "redirect:/items/getItemsForUser";
    }

}
