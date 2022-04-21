package team20.mk.ukim.finki.skit.model;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne
    private User soldByUser;

    @ManyToMany
    private List<ShoppingCart> carts;

    private boolean isApproved;


    public Item(Long id, String name, Double price, Integer quantity, Subject subject, Category category, User soldByUser) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.subject = subject;
        this.category = category;

        this.soldByUser = soldByUser;
        this.carts=new ArrayList<>();
        this.isApproved=false;
    }

    public Item(){}
}
