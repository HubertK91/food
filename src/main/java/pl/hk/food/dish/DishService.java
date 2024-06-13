package pl.hk.food.dish;

import org.springframework.stereotype.Service;
import pl.hk.food.restaurant.Restaurant;
import pl.hk.food.restaurant.RestaurantService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    private final DishRepository DishRepository;
    private final RestaurantService restaurantService;

    @PersistenceContext
    private EntityManager entityManager;

    public DishService(DishRepository DishRepository, RestaurantService restaurantService) {
        this.DishRepository = DishRepository;
        this.restaurantService = restaurantService;
    }

    public List<Dish> getProductCatalog() {
        return DishRepository.findAll();
    }

    public void addDish(Dish dish, Long restaurantId) {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
//        Long dishCount = DishRepository.countByRestaurant(restaurant);
//        Long newDishId = dishCount + 1; // generowanie nowego ID dla dania w tej restauracji

        // Generowanie kolejnej wartości z sekwencji
        Long newDishId = generateNextDishId();

        dish.setId(restaurantId, newDishId);
        dish.setRestaurant(restaurant);
        DishRepository.save(dish);
    }

    private Long generateNextDishId() {
        // Zamień nazwę sekwencji na faktyczną nazwę utworzoną w bazie danych
        return ((Number) entityManager.createNativeQuery("SELECT NEXT VALUE FOR DISH_SEQ").getSingleResult()).longValue();
    }

    public List<Dish> findAllById(List <DishId> ids) {
        return DishRepository.findAllById(ids);
    }

//    public List<Dish> findAllById(List<Long> id) {
//        return DishRepository.findAllById((DishId) id);
//    }

    public Dish findByIdDish(DishId id) {
        Optional<Dish> dish = DishRepository.findById(id);
        if (dish.isPresent()) {
            return dish.get();
        } else {
            throw new RuntimeException();
        }
    }

    public void editDish(Dish Dish) {
        DishId id = Dish.getId();
        Dish existingDish = findByIdDish(id);
        existingDish.setName(Dish.getName());
        existingDish.setPrice(Dish.getPrice());
        existingDish.setCategory(Dish.getCategory());
        DishRepository.save(existingDish);
    }

    public List<Dish> printListOfProductsInCart() {
        return DishRepository.findAllBySelected(true);
    }

    public void addProductToCart(DishId id) {
        Dish dish = findByIdDish(id);
        dish.setSelected(true);
        DishRepository.save(dish);
    }

    public void deleteProductFromCart(DishId id) {
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

    public void deleteDish(DishId id) {
        Dish dish = findByIdDish(id);
        DishRepository.delete(dish);
    }
}
