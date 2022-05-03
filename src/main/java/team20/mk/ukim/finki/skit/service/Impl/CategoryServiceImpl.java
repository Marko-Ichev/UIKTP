package team20.mk.ukim.finki.skit.service.Impl;

import org.springframework.stereotype.Service;
import team20.mk.ukim.finki.skit.model.Category;
import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.exceptions.CategoryNotFoundException;
import team20.mk.ukim.finki.skit.repository.CategoryRepository;
import team20.mk.ukim.finki.skit.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(String name) {
        if (name==null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Category c = new Category(name);
        categoryRepository.save(c);
        return c;

    }

    @Override
    public List<Category> getAllCategories() {

        List<Category> allCategories = categoryRepository.findAll();
        for (Category c : allCategories) {
            List<Item> approved = c.getAllItems().stream().filter(e -> e.isApproved()).collect(Collectors.toList());
            c.setAllItems(approved);
        }
        return allCategories;
    }

    @Override
    public List<Item> getAllItemsForCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return category.getAllItems().stream().filter(e -> e.isApproved()).collect(Collectors.toList());
    }
}
