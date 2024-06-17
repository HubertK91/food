package pl.hk.food.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hk.food.client.Client;
import pl.hk.food.client.ClientRepository;
import pl.hk.food.restaurant.Restaurant;
import pl.hk.food.restaurant.RestaurantRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final RestaurantRepository restaurantRepository;

    public MyUserDetailsService(ClientRepository clientRepository, RestaurantRepository restaurantRepository) {
        this.clientRepository = clientRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Client> userOptional = clientRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            Client client = userOptional.get();
            Set<SimpleGrantedAuthority> roles = client.getRoles()
                    .stream()
                    .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
                    .collect(Collectors.toSet());

            return new User(client.getUsername(), client.getPassword(), roles);
        }
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUsername(username);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            Set<SimpleGrantedAuthority> roles = restaurant.getRoles()
                    .stream()
                    .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
                    .collect(Collectors.toSet());

            return new User(restaurant.getUsername(), restaurant.getPassword(), roles);
        }
        throw new UsernameNotFoundException("Username " + username + "not found");
    }
}