package pl.hk.app.order;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.hk.app.client.Client;
import pl.hk.app.part.Part;

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
    @JoinTable(name = "order_parts",
            joinColumns = {@JoinColumn(name="client_order_id", referencedColumnName = "id_order")},
            inverseJoinColumns = {@JoinColumn(name="part_id", referencedColumnName="id_part")})
    private List<Part> parts = new ArrayList<>();
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

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
