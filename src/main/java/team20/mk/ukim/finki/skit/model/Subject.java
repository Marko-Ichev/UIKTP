package team20.mk.ukim.finki.skit.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "subject",fetch = FetchType.EAGER)
    private List<Item> allItems;

    public Subject(){}

    public Subject(String name) {
        this.name = name;
    }
}
