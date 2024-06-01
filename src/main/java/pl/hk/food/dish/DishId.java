package pl.hk.food.dish;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DishId implements Serializable {
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "dish_id")
    private Long dishId;

    public DishId() {
    }

    public DishId(Long restaurantId, Long dishId) {
        this.restaurantId = restaurantId;
        this.dishId = dishId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }
}

