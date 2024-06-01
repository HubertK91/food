package pl.hk.food.cartItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.hk.food.client.Client;
import pl.hk.food.dish.Dish;
import pl.hk.food.dish.DishRepository;

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

    public Integer addProduct(Long dishId, Integer quantity, Client client){
        Integer addedQuantity = quantity;
        Dish dish = dishRepo.findById(dishId).get();
        CartItem cartItem = cartRepo.findByClientAndDish(client, dish);
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

    public double updateQuantity(Long dishId, Integer quantity, Client client){
        cartRepo.updateQuantity(quantity, dishId, client.getId());
        Dish dish = dishRepo.findById(dishId).get();
        double subtotal = dish.getPrice() * quantity;
        return subtotal;
    }

    public void removeDish(Long dishId, Client client){
        cartRepo.deleteByClientAndDish(client.getId(), dishId);
    }
}
