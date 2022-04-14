package team20.mk.ukim.finki.skit.service;

import team20.mk.ukim.finki.skit.model.Category;
import team20.mk.ukim.finki.skit.model.Item;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    List<Item> getAllItemsForCategory(Long id);
}
