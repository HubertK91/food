package pl.hk.food.cartItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.hk.food.client.Client;
import pl.hk.food.client.ClientService;

import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartServices cartServices;

    @Autowired
    private ClientService clientService;

    @GetMapping("/cart")
    public String showShoppingCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByUsername(auth.getName());
        if (client == null) System.out.println("Client is null");
        List<CartItem> cartItems = cartServices.listCartItems(client);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("pageTitle", "Shopping Cart");

        return "cart/shopping_cart";
    }

    @PostMapping("/cart/add/{did}/{qty}")
    public String addDishToCart(@PathVariable("did") Long dishId, @PathVariable("qty") Integer quantity, Authentication authentication,
                                Model model) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("errorMessage", "Musisz się zalogować");
            return "Musisz się zalogować!";
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByUsername(authentication.getName());


        if (client == null) {
            model.addAttribute("errorMessage", "Musisz się zalogować");
            return "Musisz się zalogować!";
        }
        Integer addedQuantity = cartServices.addProduct(dishId, quantity, client);
        model.addAttribute("successMessage" ,addedQuantity + " sztuk dań zostało dodanych do koszyka");
        return "redirect:/cart";
    }
}


