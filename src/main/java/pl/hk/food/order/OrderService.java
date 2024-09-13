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
        // Znajdź aktualnie zalogowanego klienta
        Client client = findCurrentUser();
        if (client == null) {
            throw new IllegalStateException("No authenticated client found.");
        }

        // Przypisz zamówienie do klienta
        order.setClient(client);

        // Sprawdź, czy zamówienie zawiera dania
        List<Dish> dishes = order.getDishes();
        if (dishes == null || dishes.isEmpty()) {
            throw new IllegalStateException("Order must contain at least one dish.");
        }

        // Znajdź restaurację na podstawie pierwszego dania
        Restaurant restaurant = dishes.get(0).getRestaurant();

        // (Opcjonalnie) Sprawdź, czy wszystkie dania pochodzą z tej samej restauracji
        for (Dish dish : dishes) {
            if (!dish.getRestaurant().equals(restaurant)) {
                throw new IllegalStateException("All dishes must come from the same restaurant.");
            }
        }

        // Przypisz restaurację do zamówienia
        order.setRestaurant(restaurant);

        // Zapisz zamówienie w repozytorium
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
        // Znajdź zamówienie
        Order order = findOrderById(id);

        // Usuń zamówienie z listy zamówień klienta
        Client client = order.getClient();
        if (client != null) {
            client.getOrders().remove(order);
        }

        // Usuń zamówienie z listy zamówień restauracji
        Restaurant restaurant = order.getRestaurant();
        if (restaurant != null) {
            restaurant.getOrders().remove(order);
        }

        // Wyczyść listę dań w zamówieniu
        List<Dish> dishes = order.getDishes();
        dishes.clear();

        // Usuń zamówienie z repozytorium
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
