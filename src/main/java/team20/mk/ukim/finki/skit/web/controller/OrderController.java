package team20.mk.ukim.finki.skit.web.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team20.mk.ukim.finki.skit.model.Order;
import team20.mk.ukim.finki.skit.model.ShoppingCart;
import team20.mk.ukim.finki.skit.service.OrderService;
import team20.mk.ukim.finki.skit.service.ShoppingCartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderService orderService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/makeOrder/{id}")
    public String makeOrder(@PathVariable Long id, Model model){
        ShoppingCart cart=this.shoppingCartService.findById(id);
        this.orderService.createOrder(cart);
        return "redirect:/order/myOrders";

    }

    @GetMapping("/myOrders")
    public String myOrders(Model model, HttpServletRequest request){
        List<Order> orders=this.orderService.getOrdersForUser(request.getRemoteUser());
        model.addAttribute("orders",orders);
        model.addAttribute("bodyContent", "myOrders");
        return "master-template";
    }

    @GetMapping("/allOrders")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String allOrders(Model model){
        List<Order> all=this.orderService.getAllOrders();
        model.addAttribute("orders",all);
        model.addAttribute("bodyContent", "allOrders");
        return "master-template";
    }
}
