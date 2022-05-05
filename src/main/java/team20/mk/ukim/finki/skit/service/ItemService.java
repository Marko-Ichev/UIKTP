package team20.mk.ukim.finki.skit.service;

import team20.mk.ukim.finki.skit.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> findAll();
    Optional<Item> findById(Long id);
    Optional<Item> findByName(String name);
    Optional<Item> save(String name, Long prodId, Double price, Integer quantity, Long category, Long subjectId, String username);

    void deleteById(Long id);

    List<Item> getSubmittedItems();

    void approveItem(Long id);


}
