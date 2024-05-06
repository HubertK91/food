package pl.hk.app.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hk.app.client.Client;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

}
