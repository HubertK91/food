package pl.hk.food.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.hk.food.cartItem.CartItem;
import pl.hk.food.cartItem.ShoppingCartServices;
import pl.hk.food.client.Client;
import pl.hk.food.client.ClientService;
import pl.hk.food.dish.Dish;
import pl.hk.food.dish.DishId;
import pl.hk.food.dish.DishService;
import pl.hk.food.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class OrderController {

    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    private final OrderService orderService;
    private final DishService dishService;
    private final ShoppingCartServices shoppingCartServices;
    private final ClientService clientService;

    public OrderController(OrderService orderService, DishService dishService, ShoppingCartServices shoppingCartServices, ClientService clientService) {
        this.orderService = orderService;
        this.dishService = dishService;
        this.shoppingCartServices = shoppingCartServices;
        this.clientService = clientService;
    }

    @GetMapping("/order/catalog")
    public String getOrdersCatalog(Model model) {
        try {
            Client currentUser = orderService.findCurrentUser();
            Restaurant currentRestaurant = orderService.findCurrentRestaurant();

            List<CartItem> items = new ArrayList<>();
            if (currentUser != null) {
                items = shoppingCartServices.listCartItems(currentUser);
            } else if (currentRestaurant != null) {
                Client currentUser2 = clientService.findClientByUsername("admin");
                items = shoppingCartServices.listCartItems(currentUser2);
            }
            List<Order> orders = orderService.getOrdersCatalog();
            model.addAttribute("orders", orders);
            model.addAttribute("items", items);
            return "order/order";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting order catalog", e);
            model.addAttribute("error", "Error getting order catalog");
            return "error";
        }
    }

    @PostMapping(value = "/order/add")
    public String addOrder(@RequestParam(required = false) List<String> dishes,
                           @RequestParam(required = false) String singleDish,
                           @RequestParam int quantities,
                           final RedirectAttributes redirectAttributes) {
        if ((dishes == null || dishes.isEmpty()) && (singleDish == null || singleDish.isEmpty())) {
            redirectAttributes.addFlashAttribute("error", "No dishes selected.");
            return "redirect:/order/catalog";
        }

        Order order = new Order();
        List<DishId> dishIds = new ArrayList<>();

        try {
            // Process multiple dishes
            if (dishes != null && !dishes.isEmpty()) {
                for (String dish : dishes) {
                    String[] parts = dish.split(",");
                    if (parts.length != 2) {
                        redirectAttributes.addFlashAttribute("error", "Invalid dish format.");
                        return "redirect:/order/catalog";
                    }
                    long dishId1 = Long.parseLong(parts[0]);
                    long dishId2 = Long.parseLong(parts[1]);
                    dishIds.add(new DishId(dishId1, dishId2));
                }
            }

            // Process single dish
            if (singleDish != null && !singleDish.isEmpty()) {
                String[] parts = singleDish.split(",");
                if (parts.length != 2) {
                    redirectAttributes.addFlashAttribute("error", "Invalid dish format.");
                    return "redirect:/order/catalog";
                }
                long dishId1 = Long.parseLong(parts[0]);
                long dishId2 = Long.parseLong(parts[1]);
                dishIds.add(new DishId(dishId1, dishId2));
            }

            // Fetch dishes and add them to the order
            List<Dish> dishList = dishService.findAllById(dishIds);
            order.setDishes(dishList);
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(quantities);

            Client currentUser = orderService.findCurrentUser();
            order.setClient(currentUser);
            dishService.deleteProductsFromCart(dishList);
            orderService.addOrder(order);
            // shoppingCartServices.clearCart(currentUser);
            redirectAttributes.addFlashAttribute("createOrder", order);
            return "redirect:/order/catalog";
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid dish ID format", e);
            redirectAttributes.addFlashAttribute("error", "Invalid dish ID format.");
            return "redirect:/order/catalog";
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.SEVERE, "No such element", e);
            return "redirect:/login";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding order", e);
            redirectAttributes.addFlashAttribute("error", "Error adding order.");
            return "redirect:/order/catalog";
        }
    }


    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id, final RedirectAttributes redirectAttributes) {
        try {
            orderService.deleteOrder(id);
            redirectAttributes.addFlashAttribute("success", "Order deleted successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting order", e);
            redirectAttributes.addFlashAttribute("error", "Error deleting order.");
        }
        return "redirect:/order/catalog";
    }
}
