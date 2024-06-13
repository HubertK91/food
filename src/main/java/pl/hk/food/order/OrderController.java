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


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final DishService dishService;
    private final ShoppingCartServices shoppingCartServices;
    private final ClientService clientService;

    public OrderController(OrderService orderService, DishService dishService, ShoppingCartServices shoppingCartServices
    , ClientService clientService) {
        this.orderService = orderService;
        this.dishService = dishService;
        this.shoppingCartServices = shoppingCartServices;
        this.clientService = clientService;
    }

    @GetMapping("/order/catalog")
    public String getOrdersCatalog(Model model) {
        Client currentUser = orderService.findCurrentUser();
        List<Order> orders = orderService.getOrdersCatalog();
        List<CartItem> items = shoppingCartServices.listCartItems(currentUser);
        model.addAttribute("orders", orders);
        model.addAttribute("items", items);
        return "order/order";
    }

    @PostMapping(value = "/order/add")
    public String addOrder(@RequestParam(required = false) List<String> dishes, @RequestParam int quantities, final RedirectAttributes redirectAttributes) {
        Order order = new Order();
        List<DishId> dishIds = new ArrayList<>();
        for (String dish : dishes) {
            dishIds.add(new DishId(Long.parseLong(dish.split(",")[0]), Long.parseLong(dish.split(",")[1])));
        }
        List<Dish> dishList = dishService.findAllById(dishIds);
        order.setDishes(dishList);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantities);
        try {
            Client currentUser = orderService.findCurrentUser();
            order.setClient(currentUser);
            dishService.deleteProductsFromCart(dishList);
            orderService.addOrder(order);
            redirectAttributes.addFlashAttribute("createOrder", order);
            return "redirect:/order/catalog";
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
