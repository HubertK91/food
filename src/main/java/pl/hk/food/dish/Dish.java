package pl.hk.food.dish;

import pl.hk.food.Category;
import pl.hk.food.order.Order;
import pl.hk.food.restaurant.Restaurant;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dish {
    @EmbeddedId
    private DishId id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dish")
    private Long id2;
    @Column(name = "dish_name", nullable = false)
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "selected", nullable = false, columnDefinition = "boolean default false" )
    private boolean selected;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToMany(mappedBy = "dishes", cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();
    @ManyToOne
    @MapsId("restaurantId")
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;
    public Dish() {
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public DishId getId() {
        return id;
    }

    public void setId(Long restaurantId, Long dishId) {
        this.id = new DishId(restaurantId, dishId);
    }
}
