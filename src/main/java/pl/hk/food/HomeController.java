package pl.hk.food;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hk.food.mail.MailForm;
import pl.hk.food.mail.MailService;
import pl.hk.food.restaurant.Restaurant;
import pl.hk.food.restaurant.RestaurantService;

import java.util.List;

@Controller
public class HomeController {
    private final MailService mailService;
    private final RestaurantService restaurantService;

    public HomeController(MailService mailService, RestaurantService restaurantService) {
        this.mailService = mailService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Restaurant> restaurants = restaurantService.getProductCatalog();
        model.addAttribute("restaurants", restaurants);
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
