package pl.hk.food.order;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.hk.food.client.Client;
import pl.hk.food.dish.Dish;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "order_dishes",
            joinColumns = {@JoinColumn(name="client_order_id", referencedColumnName = "id_order")},
            inverseJoinColumns = {
            @JoinColumn(name="restaurant_id", referencedColumnName="restaurant_id"),
            @JoinColumn(name="dish_id", referencedColumnName="dish_id")})
    private List<Dish> dishes = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
