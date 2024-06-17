package pl.hk.food.restaurant;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.hk.food.client.Client;
import pl.hk.food.security.RestaurantRole;
import pl.hk.food.security.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    public RestaurantService(RestaurantRepository RestaurantRepository, PasswordEncoder passwordEncoder) {
        this.restaurantRepository = RestaurantRepository;
        this.passwordEncoder = passwordEncoder;
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
        restaurant1.setCity(restaurant.getCity());
        restaurant1.setPhone(restaurant.getPhone());
        restaurant1.setEmail(restaurant.getEmail());
        restaurant1.setStreetAddress(restaurant.getStreetAddress());
        restaurantRepository.save(restaurant1);
    }

    public void registerRestaurant(String username, String name, String rawPassword
    , String city, String streetAddress, String phone, String email) {
        Restaurant restaurantToAdd = new Restaurant();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        restaurantToAdd.setPassword(encryptedPassword);

        System.out.println("Raw password: " + rawPassword);
        System.out.println("Encrypted password: " + encryptedPassword);

        restaurantToAdd.setUsername(username);
        restaurantToAdd.setCity(city);
        restaurantToAdd.setPhone(phone);
        restaurantToAdd.setEmail(email);
        restaurantToAdd.setStreetAddress(streetAddress);
        restaurantToAdd.setName(name);

        // Tworzenie i przypisanie roli
        RestaurantRole role = new RestaurantRole(restaurantToAdd, Role.ROLE_RESTAURANT);
        restaurantToAdd.setRoles(new HashSet<>());
        restaurantToAdd.getRoles().add(role);

        restaurantRepository.save(restaurantToAdd);
    }

    public Restaurant findCurrentRestaurant(String username) {
        Optional<Restaurant> restaurant = restaurantRepository.findByUsername(username);
        if (restaurant.isPresent()) {
            return restaurant.get();
        } else {
            return null;
        }
    }
}
