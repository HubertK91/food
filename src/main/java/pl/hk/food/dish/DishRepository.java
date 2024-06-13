package pl.hk.food.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.hk.food.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, DishId> {
    List<Dish> findAllBySelected(boolean selected);
    List<Dish> findByRestaurantId(Long restaurantId);

    Optional<Dish> findById(DishId id);

    Long countByRestaurant(Restaurant restaurant);

    List<Dish> findAllById(DishId id);

}
