package pl.hk.app.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hk.app.client.Client;
import pl.hk.app.client.ClientService;

import java.util.List;

@RequestMapping("/restaurant")
@Controller
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/list")
    public String getProductCatalog(Model model) {
        List<Restaurant> restaurants = restaurantService.getProductCatalog();
        model.addAttribute("restaurants", restaurants);
        return "client/listClient";
    }

    @GetMapping("/edit")
    public String editClient(@RequestParam Long id, Model model) {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "client/editClient";
    }

    @PostMapping("/edit")
    public String editRestaurant(Restaurant restaurant) {
        restaurantService.editRestaurant(restaurant);
        return "redirect:/";
    }
}

