package pl.hk.food.restaurant;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository RestaurantRepository) {
        this.restaurantRepository = RestaurantRepository;
    }

    public List<Restaurant> getProductCatalog() {
        return restaurantRepository.findAll();
    }

    public Restaurant findRestaurantById(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        } else {
            throw new RuntimeException();
        }
    }

    public void editRestaurant(Restaurant restaurant) {
        Restaurant restaurant1 = findRestaurantById(restaurant.getId());
        restaurant1.setName(restaurant.getName());
        restaurant1.setAddress(restaurant.getAddress());
        restaurantRepository.save(restaurant1);
    }

    public void registerRestaurant(String username, String name, String rawPassword, String address) {
        Restaurant restaurantToAdd = new Restaurant();

        restaurantToAdd.setUsername(username);
        restaurantToAdd.setAddress(address);
        restaurantToAdd.setName(name);
        restaurantToAdd.setPassword(rawPassword);

        restaurantRepository.save(restaurantToAdd);
    }
}
