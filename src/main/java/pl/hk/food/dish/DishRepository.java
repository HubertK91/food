package pl.hk.food.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.hk.food.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, DishId> {
    List<Dish> findAllBySelected(boolean selected);
    List<Dish> findByRestaurantId(Long restaurantId);

    Optional<Dish> findById(DishId id);

    Long countByRestaurant(Restaurant restaurant);

    List<Dish> findAllById(DishId id);

    // Zapytanie JPQL, aby znaleźć maksymalne dishId dla danego restaurantId
    @Query("SELECT COALESCE(MAX(d.id.dishId), 0) FROM Dish d WHERE d.id.restaurantId = :restaurantId")
    Long findMaxDishIdByRestaurantId(@Param("restaurantId") Long restaurantId);

}
