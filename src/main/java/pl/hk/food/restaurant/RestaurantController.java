package pl.hk.food.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hk.food.order.OrderService;

import java.util.List;

@RequestMapping("/restaurant")
@Controller
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final OrderService orderService;

    public RestaurantController(RestaurantService restaurantService, OrderService orderService) {
        this.restaurantService = restaurantService;
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String getProductCatalog(Model model) {
        List<Restaurant> restaurants = restaurantService.getProductCatalog();
        Restaurant currentRestaurant = orderService.findCurrentRestaurant();
        model.addAttribute("currentRestaurant", currentRestaurant);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant/listRestaurant";
    }

    @GetMapping("/edit")
    public String editClient(@RequestParam Long id, Model model) {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "restaurant/editRestaurant";
    }

    @PostMapping("/edit")
    public String editRestaurant(Restaurant restaurant) {
        restaurantService.editRestaurant(restaurant);
        return "redirect:/";
    }
}

