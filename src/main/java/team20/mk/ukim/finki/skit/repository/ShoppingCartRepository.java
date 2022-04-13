package team20.mk.ukim.finki.skit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team20.mk.ukim.finki.skit.model.ShoppingCart;
import team20.mk.ukim.finki.skit.model.enumerations.ShoppingCartStatus;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUser_UsernameAndStatus(String username, ShoppingCartStatus status);
}
