package pl.hk.app.dish;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hk.app.Category;
import pl.hk.app.order.Order;

import java.util.List;

@RequestMapping("/dish")
@Controller
public class DishController {
    private final DishService DishService;

    public DishController(DishService DishService) {
        this.DishService = DishService;
    }

    @GetMapping("/menu")
    public String getProductCatalog(Model model) {
        List<Dish> dishes = DishService.getProductCatalog();
        model.addAttribute("dishes", dishes);
        return "dish/menu";
    }

    @GetMapping("/add")
    public String addDish(Model model) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("categories", Category.values());
        return "dish/addDish";
    }

    @PostMapping("/add")
    public String addDish(Dish Dish) {
        DishService.addDish(Dish);
        return "redirect:/menu";
    }

    @GetMapping("/edit")
    public String editDish(@RequestParam Long id, Model model) {
        Dish Dish = DishService.findByIdDish(id);
        model.addAttribute("dish", Dish);
        model.addAttribute("categories", Category.values());
        return "dish/editDish";
    }

    @PostMapping("/edit")
    public String editDish(Dish Dish) {
        DishService.editDish(Dish);
        return "redirect:/menu";
    }

    @GetMapping("/cart")
    public String printListOfProductsInCart(Model model, @ModelAttribute(name = "createOrder") Order order) {
        List<Dish> dishes = DishService.printListOfProductsInCart();
        model.addAttribute("createOrder", order);
        model.addAttribute("dishes", dishes);
        return "cart";
    }

    @PostMapping("/cart")
    public String addProductToCart(@RequestParam Long id) {
        DishService.addProductToCart(id);
        return "redirect:/menu";
    }

    @PostMapping("/catalog")
    public String deleteProductFromCart(@RequestParam Long id) {
        DishService.deleteProductFromCart(id);
        return "redirect:/cart";
    }
}
