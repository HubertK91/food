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
import org.springframework.web.bind.annotation.ResponseBody;
import pl.hk.food.client.Client;
import pl.hk.food.client.ClientService;
import pl.hk.food.order.OrderService;
import pl.hk.food.restaurant.Restaurant;
import pl.hk.food.restaurant.RestaurantService;

import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartServices cartServices;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/cart")
    public String showShoppingCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByUsername(auth.getName());
        Restaurant restaurant = restaurantService.findCurrentRestaurant(auth.getName());
        if (client == null&&restaurant == null){
            return "redirect:/login";
        }
        List<CartItem> cartItems = cartServices.listCartItems(client);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("pageTitle", "Shopping Cart");

        return "cart/shopping_cart";
    }

    @PostMapping("/cart/add/{rid}/{did}/{qty}")
    public String addDishToCart(@PathVariable("rid") Long restaurantId, @PathVariable("did") Long dishId, @PathVariable("qty") Integer quantity, Authentication authentication,
                                Model model) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("errorMessage", "Musisz się zalogować");
            return "redirect:/login";
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByUsername(authentication.getName());


        if (client == null) {
            model.addAttribute("errorMessage", "Musisz się zalogować");
            return "redirect:/login";
        }
        Integer addedQuantity = cartServices.addProduct(restaurantId, dishId, quantity, client);   //
        model.addAttribute("successMessage" ,addedQuantity + " sztuk dań zostało dodanych do koszyka");
        return "redirect:/cart";
    }
    @ResponseBody
    @PostMapping("/cart/update/{rid}/{did}/{qty}")
    public String updateQuantity(@PathVariable("rid") Long restaurantId, @PathVariable("did") Long dishId, @PathVariable("qty") Integer quantity, Authentication authentication,
                                Model model) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("errorMessage", "Musisz się zalogować, aby zaaktualizować ilość!");
            return "Musisz się zalogować,aby zaktualizować ilość!";
        }

        Client client = clientService.findClientByUsername(authentication.getName());


        if (client == null) {
            model.addAttribute("errorMessage", "Musisz się zalogować, aby zaktualizować ilość");
            return "Musisz się zalogować, aby zaktualizować ilość!";
        }
        double subtotal = cartServices.updateQuantity(restaurantId, dishId, quantity, client);
        return String.valueOf(subtotal);
    }

    @ResponseBody
    @PostMapping("/cart/remove/{did}/{rid}")
    public String removeDishFromCart(@PathVariable("did") Long dishId, Authentication authentication,
                                 Model model, @PathVariable("rid") Long restaurantId) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("errorMessage", "Musisz się zalogować, aby usunąć danie");
            return "Musisz się zalogować, aby usunąć danie!";
        }

        Client client = clientService.findClientByUsername(authentication.getName());


        if (client == null) {
            model.addAttribute("errorMessage", "Musisz się zalogować, aby usunąć danie");
            return "Musisz się zalogować, aby usunąć danie!";
        }
        cartServices.removeDish(dishId, client, restaurantId);
        return "Danie zostało usunięte z twojego koszyka";
    }
}


