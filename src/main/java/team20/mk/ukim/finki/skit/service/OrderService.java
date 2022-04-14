package team20.mk.ukim.finki.skit.service;

import team20.mk.ukim.finki.skit.model.Order;
import team20.mk.ukim.finki.skit.model.ShoppingCart;

import java.util.List;

public interface OrderService {


    Order createOrder(ShoppingCart shoppingCart);

    List<Order> getOrdersForUser(String username);
    List<Order> getAllOrders();
}
