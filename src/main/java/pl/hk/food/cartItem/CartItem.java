package pl.hk.food.cartItem;

import pl.hk.food.client.Client;
import pl.hk.food.dish.Dish;


import javax.persistence.*;

@Entity
@Table(name="cart items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Dish_id")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "Client_id")
    private Client client;

    private int quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
