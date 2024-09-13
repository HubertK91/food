package pl.hk.food.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.hk.food.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUsername(String username);


    // Znajdź klientów na podstawie restauracji przez zamówienie
    @Query("SELECT c FROM Client c JOIN c.orders o WHERE o.restaurant = :restaurant")
    List<Client> findByRestaurant(@Param("restaurant") Restaurant restaurant);
}
