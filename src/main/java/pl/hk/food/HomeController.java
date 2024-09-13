package pl.hk.food;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hk.food.mail.MailForm;
import pl.hk.food.mail.MailService;
import pl.hk.food.order.OrderService;
import pl.hk.food.restaurant.Restaurant;
import pl.hk.food.restaurant.RestaurantService;

import java.util.List;

@Controller
public class HomeController {
    private final MailService mailService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;

    public HomeController(MailService mailService, RestaurantService restaurantService, OrderService orderService) {
        this.mailService = mailService;
        this.restaurantService = restaurantService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Restaurant> restaurants = restaurantService.getProductCatalog();
        Restaurant currentRestaurant = orderService.findCurrentRestaurant();
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("currentRestaurant", currentRestaurant);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            model.addAttribute("username", username);
        }

        return "main/home";
    }


    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("sender",new MailForm());
        return "contact/contact";
    }

    @PostMapping("/sent")
    public String sendMail(MailForm sender) {
        mailService.sendMail(sender);
        return "contact/result";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "main/error_forbidden";
    }
}
