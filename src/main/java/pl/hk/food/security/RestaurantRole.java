package pl.hk.food.security;

import pl.hk.food.client.Client;
import pl.hk.food.restaurant.Restaurant;

import javax.persistence.*;

@Entity
public class RestaurantRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Restaurant restaurant;

    @Enumerated(EnumType.STRING)
    private Role role;

    public RestaurantRole() {
    }

    public RestaurantRole(Restaurant restaurant, Role role) {
        this.restaurant = restaurant;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
