package team20.mk.ukim.finki.skit.service;

import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<Item> listAllProductsInShoppingCart(Long cartId);
    ShoppingCart addProductToShoppingCart(String username, Long productId);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart findById(Long id);
    void deleteProductFromShoppingCart(Long cartId, Long prodId);
}
