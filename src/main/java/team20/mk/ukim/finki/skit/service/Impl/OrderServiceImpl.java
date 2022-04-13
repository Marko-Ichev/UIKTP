package team20.mk.ukim.finki.skit.service.Impl;

import org.springframework.stereotype.Service;
import team20.mk.ukim.finki.skit.model.Order;
import team20.mk.ukim.finki.skit.model.ShoppingCart;
import team20.mk.ukim.finki.skit.model.User;
import team20.mk.ukim.finki.skit.repository.OrderRepository;
import team20.mk.ukim.finki.skit.repository.UserRepository;
import team20.mk.ukim.finki.skit.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order createOrder(ShoppingCart shoppingCart) {
        Order order=new Order();
        order.setItemsInOrder(shoppingCart.getItems());
        order.setTotalAmount(shoppingCart.getItems().stream().mapToDouble(i -> i.getPrice()).sum());
        order.setUser(shoppingCart.getUser());
        orderRepository.save(order);

        User user=this.userRepository.findByUsername(shoppingCart.getUser().getUsername()).get();
        user.getOrders().add(order);
        this.userRepository.save(user);

        return order;
    }

    @Override
    public List<Order> getOrdersForUser(String username) {
        User user=this.userRepository.findByUsername(username).get();
        return user.getOrders();
    }
}
