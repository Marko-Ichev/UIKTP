package team20.mk.ukim.finki.skit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team20.mk.ukim.finki.skit.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
