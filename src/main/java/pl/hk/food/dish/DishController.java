package pl.hk.food.dish;

import ch.qos.logback.core.joran.conditional.IfAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.hk.food.Category;
import pl.hk.food.order.Order;
import pl.hk.food.restaurant.Restaurant;
import pl.hk.food.restaurant.RestaurantService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/dish")
@Controller
public class DishController {
    private final DishService DishService;
    private final RestaurantService RestaurantService;
    private final HttpSession httpSession;

    public DishController(DishService DishService, RestaurantService RestaurantService, HttpSession httpSession) {
        this.DishService = DishService;
        this.RestaurantService = RestaurantService;
        this.httpSession = httpSession;
    }

    @GetMapping("/menu")
    public String getProductCatalog(Model model, @RequestParam(required = false) Long id) {
        Restaurant restaurant = RestaurantService.findRestaurantById(id);
        List<Dish> dishes = restaurant.getDishes();
        model.addAttribute("dishes", dishes);
        model.addAttribute("id", restaurant.getId());
        return "dish/menu";
    }

    @PostMapping("/menu")
    public String getProductCatalog(@RequestParam(value = "restaurantId", required = false) Long restaurantId, @RequestParam(required = false) Long id, Model model) {

        Restaurant restaurant;
        if(restaurantId!=null){
            // Find the restaurant by its ID
            restaurant = RestaurantService.findRestaurantById(restaurantId);

            // Get the list of dishes associated with the restaurant
            List<Dish> dishes = restaurant.getDishes();

            // Add the list of dishes to the model
            model.addAttribute("dishes", dishes);


            // Return the view name for displaying the menu

        }else{
            // Find the restaurant by its ID
            restaurant = RestaurantService.findRestaurantById(id);

            // Get the list of dishes associated with the restaurant
            List<Dish> dishes = restaurant.getDishes();

            // Add the list of dishes to the model
            model.addAttribute("dishes", dishes);

            // Return the view name for displaying the menu
        }
        return "dish/menu";


    }

    @GetMapping("/add")
    public String addDish(Model model, @RequestParam Long id) {
        List<Dish> dishes = DishService.findDishesByRestaurantId(id); //
        Restaurant restaurant = RestaurantService.findRestaurantById(id);
        model.addAttribute("dish", new Dish());
        model.addAttribute("dishes", dishes);  //
        model.addAttribute("categories", Category.values());
        model.addAttribute("restaurant", id);
        model.addAttribute("Restaurant", restaurant);
        return "dish/addDish";
    }

    @PostMapping("/add")
    public String addDish(Dish dish, @RequestParam(value = "id", required = false) Long id) {
        Restaurant restaurant = RestaurantService.findRestaurantById(id);
        dish.setRestaurant(restaurant);
        DishService.addDish(dish);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editDish(@RequestParam Long id, Model model) {
        Dish Dish = DishService.findByIdDish(id);
        model.addAttribute("dish", Dish);
        model.addAttribute("categories", Category.values());
        return "dish/editDish";
    }

    @PostMapping("/edit")
    public String editDish(Dish dish) {
        DishService.editDish(dish);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteDish(@RequestParam Long id) {
        DishService.deleteDish(id);
        return "redirect:/menu";
    }
}
