package pl.hk.food.order;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.hk.food.client.Client;
import pl.hk.food.client.ClientRepository;
import pl.hk.food.dish.Dish;
import pl.hk.food.restaurant.Restaurant;
import pl.hk.food.restaurant.RestaurantRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Order> getOrdersCatalog() {
        return orderRepository.findAll();
    }

    public List<Order> getAllClientOrders() {
        return orderRepository.findAll();
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public Order findOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new RuntimeException();
        }
    }

    public void deleteOrder(Long id) {
        Order order = findOrderById(id);
        List<Dish> dishes = order.getDishes();
        dishes.clear();
        orderRepository.delete(order);
    }

    public Client findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("User is not authenticated");
            return null; // Zwraca null, gdy użytkownik nie jest uwierzytelniony
        }

        String username = authentication.getName();
        System.out.println("Looking for user: " + username);
        return clientRepository.findByUsername(username).orElse(null);
    }

    public Restaurant findCurrentRestaurant() throws NoSuchElementException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String username = authentication.getName();
        return restaurantRepository.findByUsername(username).orElse(null);
    }
}
