package pl.hk.food.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.hk.food.client.Client;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAllBySelected(boolean selected);
    List<Dish> findByRestaurantId(Long restaurantId);

    Optional<Dish> findById(Long id);
}
