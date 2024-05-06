package pl.hk.food.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("/client")
@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/list")
    public String getProductCatalog(Model model) {
        List<Client> clients = clientService.getProductCatalog();
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
}

