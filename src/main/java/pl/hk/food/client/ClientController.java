package pl.hk.food.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hk.food.dish.DishId;
import pl.hk.food.order.OrderService;
import pl.hk.food.restaurant.Restaurant;

import java.util.List;
@RequestMapping("/client")
@Controller
public class ClientController {
    private final ClientService clientService;
    private final OrderService orderService;

    public ClientController(ClientService clientService, OrderService orderService) {
        this.clientService = clientService;
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String getProductCatalog(Model model) {
        List<Client> clients = clientService.getProductCatalog();
        Client currentUser = orderService.findCurrentUser();
        Restaurant currentRestaurant = orderService.findCurrentRestaurant();
        model.addAttribute("currentRestaurant", currentRestaurant);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("clients", clients);
        return "client/listClient";
    }

    @GetMapping("/edit")
    public String editClient(@RequestParam Long id, Model model) {
        Client client = clientService.findClientById(id);
        model.addAttribute("client", client);
        return "client/editClient";
    }

    @PostMapping("/edit")
    public String editClient(Client client) {
        clientService.editClient(client);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteDish(@RequestParam Long id) {
        clientService.deleteClient(id);
        return "redirect:/client/list";
    }
}

