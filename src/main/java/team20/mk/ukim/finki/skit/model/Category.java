package team20.mk.ukim.finki.skit.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    private List<Item> allItems;

    public Category(){}

    public Category(String name) {
        this.name = name;
    }
}
