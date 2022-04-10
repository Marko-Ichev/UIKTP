package team20.mk.ukim.finki.skit.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    private List<Item> items;
}
