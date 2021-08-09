package pl.hk.app.security;

import pl.hk.app.client.Client;

import javax.persistence.*;

@Entity
public class ClientRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client user;

    @Enumerated(EnumType.STRING)
    private Role role;

    public ClientRole() {
    }

    public ClientRole(Client user, Role role) {
        this.user = user;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }
}
