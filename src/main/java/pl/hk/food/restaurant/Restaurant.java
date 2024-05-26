package pl.hk.food.restaurant;

import pl.hk.food.Category;
import pl.hk.food.dish.Dish;
import pl.hk.food.order.Order;
import pl.hk.food.security.ClientRole;
import pl.hk.food.security.RestaurantRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurant")
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;

    private String username;

    private String password;

    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<RestaurantRole> roles;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "restaurant")
    private List<Dish> dishes = new ArrayList<>();

    private String menu;

    public Restaurant() {
    }

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RestaurantRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<RestaurantRole> roles) {
        this.roles = roles;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
