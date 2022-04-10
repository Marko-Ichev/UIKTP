package team20.mk.ukim.finki.skit.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Item> itemsInOrder;

    @ManyToOne
    private User user;

    private Double totalAmount;



}
