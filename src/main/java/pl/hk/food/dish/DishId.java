package pl.hk.food.dish;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishId dishId1 = (DishId) o;
        return Objects.equals(restaurantId, dishId1.restaurantId) && Objects.equals(dishId, dishId1.dishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, dishId);
    }
}

