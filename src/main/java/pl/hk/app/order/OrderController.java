package pl.hk.app.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.hk.app.client.Client;
import pl.hk.app.dish.Dish;
import pl.hk.app.dish.DishService;


import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final DishService dishService;

    public OrderController(OrderService orderService, DishService dishService) {
        this.orderService = orderService;
        this.dishService = dishService;
    }

    @GetMapping("/order/catalog")
    public String getOrdersCatalog(Model model) {
        List<Order> orders = orderService.getOrdersCatalog();
        model.addAttribute("orders", orders);
        return "order/order";
    }

    @PostMapping(value = "/order/add")
    public String addOrder(final RedirectAttributes redirectAttributes) {
        Order order = new Order();
        List<Dish> dishes = dishService.printListOfProductsInCart();
        order.setDishes(dishes);
        try {
            Client currentUser = orderService.findCurrentUser();
            order.setClient(currentUser);
            dishService.deleteProductsFromCart(dishes);
            orderService.addOrder(order);
            redirectAttributes.addFlashAttribute("createOrder", order);
            return "redirect:/dish/cart";
        } catch (NoSuchElementException e) {
            return "redirect:/login";
        }
    }

    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/order/catalog";
    }
}
