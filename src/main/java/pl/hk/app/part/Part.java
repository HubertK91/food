package pl.hk.app.part;

import pl.hk.app.Category;
import pl.hk.app.order.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_part")
    private Long id;
    @Column(name = "part_name", nullable = false)
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "selected", nullable = false, columnDefinition = "boolean default false" )
    private boolean selected;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToMany(mappedBy = "parts")
    private List<Order> orders = new ArrayList<>();

    public Part() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
