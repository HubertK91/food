package pl.hk.food.dish;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    private final DishRepository DishRepository;

    public DishService(DishRepository DishRepository) {
        this.DishRepository = DishRepository;
    }

    public List<Dish> getProductCatalog() {
        return DishRepository.findAll();
    }

    public void addDish(Dish dish) {
        DishRepository.save(dish);
    }

    public Dish findByIdDish(Long id) {
        Optional<Dish> dish = DishRepository.findById(id);
        if (dish.isPresent()) {
            return dish.get();
        } else {
            throw new RuntimeException();
        }
    }

    public void editDish(Dish Dish) {
        Dish menu1 = findByIdDish(Dish.getId());
        menu1.setName(Dish.getName());
        menu1.setPrice(Dish.getPrice());
        menu1.setCategory(Dish.getCategory());
        DishRepository.save(menu1);
    }

    public List<Dish> printListOfProductsInCart() {
        return DishRepository.findAllBySelected(true);
    }

    public void addProductToCart(Long id) {
        Dish dish = findByIdDish(id);
        dish.setSelected(true);
        DishRepository.save(dish);
    }

    public void deleteProductFromCart(Long id) {
        Dish dish = findByIdDish(id);
        dish.setSelected(false);
        DishRepository.save(dish);
    }

    public void deleteProductsFromCart(List<Dish> dishes) {
        for (Dish dish : dishes) {
            dish.setSelected(false);
            DishRepository.save(dish);
        }
    }

    public List<Dish> findDishesByRestaurantId(Long restaurantId) {
    return DishRepository.findByRestaurantId(restaurantId);
    }

    public void deleteDish(Long id) {
        Dish dish = findByIdDish(id);
        DishRepository.delete(dish);
    }
}
