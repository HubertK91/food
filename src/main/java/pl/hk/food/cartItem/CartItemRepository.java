package pl.hk.food.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hk.food.client.Client;
import pl.hk.food.dish.Dish;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

    public List<CartItem> findByClient(Client client);

    public CartItem findByClientAndDish(Client client, Dish dish);

    public void updateQuantity(Integer quantity, Integer dishId, Integer clientId);

}
