package pl.hk.food.cartItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.hk.food.client.Client;
import pl.hk.food.dish.Dish;
import pl.hk.food.dish.DishId;
import pl.hk.food.dish.DishRepository;
import pl.hk.food.restaurant.Restaurant;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShoppingCartServices {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private DishRepository dishRepo;

    public List<CartItem> listCartItems(Client client) {
        return cartRepo.findByClient(client);
    }

    public Integer addProduct(Long restaurantId, Long dishId, Integer quantity, Client client){
        Integer addedQuantity = quantity;
        DishId dishId1 = new DishId(restaurantId, dishId);
        Dish dish = dishRepo.findById(dishId1).get();
        CartItem cartItem = cartRepo.findByClientAndDish(client, dish);  //
        if(cartItem != null){
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        }else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setClient(client);
            cartItem.setDish(dish);
        }

        cartRepo.save(cartItem);
        return addedQuantity;
    };

    public double updateQuantity(Long restaurantId, Long dishId, Integer quantity, Client client){
        cartRepo.updateQuantity(quantity, dishId, restaurantId, client.getId());
        DishId dishId1 = new DishId(restaurantId, dishId);
        Dish dish = dishRepo.findById(dishId1).get();
        double subtotal = dish.getPrice() * quantity;
        return subtotal;
    }

    public void removeDish(Long dishId, Client client, Long restaurantId){
        DishId dishIdObj = new DishId(restaurantId, dishId);
        cartRepo.deleteByClientAndDishAndRestaurant(client.getId(), dishIdObj);
    }

    public void clearCart(Client client) {
        cartRepo.deleteByClient(client);
    }
}
