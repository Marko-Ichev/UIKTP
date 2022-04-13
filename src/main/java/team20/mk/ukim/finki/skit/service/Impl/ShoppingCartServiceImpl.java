package team20.mk.ukim.finki.skit.service.Impl;


import org.springframework.stereotype.Service;
import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.ShoppingCart;
import team20.mk.ukim.finki.skit.model.User;
import team20.mk.ukim.finki.skit.model.enumerations.ShoppingCartStatus;
import team20.mk.ukim.finki.skit.repository.ShoppingCartRepository;
import team20.mk.ukim.finki.skit.repository.UserRepository;
import team20.mk.ukim.finki.skit.service.ItemService;
import team20.mk.ukim.finki.skit.service.ShoppingCartService;

import java.util.List;

@Service

public class ShoppingCartServiceImpl implements ShoppingCartService {


    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ItemService productService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ItemService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Item> listAllProductsInShoppingCart(Long cartId) {
        if(!shoppingCartRepository.findById(cartId).isPresent()){
            throw new RuntimeException();
        }
        return shoppingCartRepository.findById(cartId).get().getItems();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {

        return this.shoppingCartRepository
                .findByUser_UsernameAndStatus(username, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    User user = this.userRepository.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException());
                    ShoppingCart shoppingCart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });


    }
    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Item product = this.productService.findById(productId)
                .orElseThrow(() -> new RuntimeException());
        if(shoppingCart.getItems()
                .stream().filter(i -> i.getId().equals(productId)).count() > 0)
            throw new RuntimeException();
        shoppingCart.getItems().add(product);
        return this.shoppingCartRepository.save(shoppingCart);

    }
}
