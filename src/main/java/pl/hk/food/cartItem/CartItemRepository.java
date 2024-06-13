package pl.hk.food.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.hk.food.client.Client;
import pl.hk.food.dish.Dish;
import pl.hk.food.dish.DishId;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

    public List<CartItem> findByClient(Client client);

    public CartItem findByClientAndDish(Client client, Dish dish);

    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.dish.id.dishId = ?2 AND c.dish.id.restaurantId = ?3 AND c.client.id = ?3")
    @Modifying
    public void updateQuantity(Integer quantity, Long dishId, Long restaurantId, Long clientId);

    @Query("DELETE FROM CartItem c WHERE c.client.id = ?1 AND c.dish.id = ?2")
    @Modifying
    public void deleteByClientAndDishAndRestaurant(Long clientId, DishId dishId);


}
