package team20.mk.ukim.finki.skit.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

    @ManyToOne
    private Subject subject;
    @ManyToOne
    private Category category;

    @ManyToMany
    private List<Keyword> keywords;

    @ManyToOne
    private User soldByUser;

    @ManyToMany
    private List<User> boughtByUsers;

    @ManyToMany
    private List<ShoppingCart> carts;

    @ManyToMany
    private List<Order> ordersForItem;


}
