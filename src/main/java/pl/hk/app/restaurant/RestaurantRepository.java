package pl.hk.app.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.hk.app.client.Client;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByUsername(String username);
}
