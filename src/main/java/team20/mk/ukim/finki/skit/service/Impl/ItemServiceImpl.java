package team20.mk.ukim.finki.skit.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.mk.ukim.finki.skit.model.*;
import team20.mk.ukim.finki.skit.repository.*;
import team20.mk.ukim.finki.skit.service.ItemService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository productRepository;
    private final SubjectRepository subjectRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;



    public ItemServiceImpl(ItemRepository itemRepository,
                           SubjectRepository subjectRepository,
                           CategoryRepository categoryRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository) {
        this.productRepository = itemRepository;
        this.subjectRepository=subjectRepository;

        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }



    @Override
    public List<Item> findAll() {
        List<Item> all=this.productRepository.findAll();
        return all.stream().filter(e -> e.isApproved()).collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Optional<Item> findByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Item> save(String name, Long prodId, Double price, Integer quantity, Long categoryId, Long subjectId, String username) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException());

        Subject subject=this.subjectRepository.findById(subjectId).get();

        User soldBy=this.userRepository.findByUsername(username).get();

        Item toReturn;
        if (prodId != null && productRepository.findById(prodId).isPresent()) {
            Item productToUpdate = productRepository.findById(prodId).get();
            productToUpdate.setName(name);
            productToUpdate.setSubject(subject);
            productToUpdate.setCategory(category);
            productToUpdate.setPrice(price);
            productToUpdate.setQuantity(quantity);
            toReturn = productRepository.save(productToUpdate);


            return Optional.of(toReturn);
        } else {
            toReturn = productRepository.save(
                    new Item(prodId,name, price, quantity, subject, category,soldBy));

            return Optional.of(toReturn);
        }


    }



    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<Item> getSubmittedItems() {
        return productRepository.findAll().stream().filter(e -> !e.isApproved()).collect(Collectors.toList());
    }

    @Override
    public void approveItem(Long id) {
        Item item=productRepository.findById(id).get();
        item.setApproved(true);
        productRepository.save(item);
    }
}
