package team20.mk.ukim.finki.skit.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.mk.ukim.finki.skit.model.Item;
import team20.mk.ukim.finki.skit.model.Order;
import team20.mk.ukim.finki.skit.model.ShoppingCart;
import team20.mk.ukim.finki.skit.model.User;
import team20.mk.ukim.finki.skit.repository.OrderRepository;
import team20.mk.ukim.finki.skit.repository.ShoppingCartRepository;
import team20.mk.ukim.finki.skit.repository.UserRepository;
import team20.mk.ukim.finki.skit.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public Order createOrder(ShoppingCart shoppingCart) {
        Order order=new Order();
        List<Item> items=new ArrayList<>();
        items.addAll(shoppingCart.getItems());
        order.setItemsInOrder(items);
        order.setTotalAmount(shoppingCart.getItems().stream().mapToDouble(i -> i.getPrice()).sum());
        order.setUser(shoppingCart.getUser());
        orderRepository.save(order);

      /*  User user=this.userRepository.findByUsername(shoppingCart.getUser().getUsername()).get();
        user.getOrders().add(order);
        this.userRepository.save(user);*/

        shoppingCart.getItems().clear();
        this.shoppingCartRepository.save(shoppingCart);
        return order;
    }

    @Override
    public List<Order> getOrdersForUser(String username) {
        User user=this.userRepository.findByUsername(username).get();
        return user.getOrders();
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }
}
