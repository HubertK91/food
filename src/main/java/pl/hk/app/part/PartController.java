package pl.hk.app.part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hk.app.Category;
import pl.hk.app.order.Order;

import java.util.List;

@RequestMapping("/part")
@Controller
public class PartController {
    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/catalog")
    public String getProductCatalog(Model model) {
        List<Part> parts = partService.getProductCatalog();
        model.addAttribute("parts", parts);
        return "part/catalog";
    }

    @GetMapping("/add")
    public String addPart(Model model) {
        model.addAttribute("partToAdd", new Part());
        model.addAttribute("categories", Category.values());
        return "part/addPart";
    }

    @PostMapping("/add")
    public String addPart(Part part) {
        partService.addPart(part);
        return "redirect:/catalog";
    }

    @GetMapping("/edit")
    public String editPart(@RequestParam Long id, Model model) {
        Part part = partService.findByIdPart(id);
        model.addAttribute("part", part);
        model.addAttribute("categories", Category.values());
        return "part/editPart";
    }

    @PostMapping("/edit")
    public String editPart(Part part) {
        partService.editPart(part);
        return "redirect:/catalog";
    }

    @GetMapping("/cart")
    public String printListOfProductsInCart(Model model, @ModelAttribute(name = "createOrder") Order order) {
        List<Part> parts = partService.printListOfProductsInCart();
        model.addAttribute("createOrder", order);
        model.addAttribute("parts", parts);
        return "cart";
    }

    @PostMapping("/cart")
    public String addProductToCart(@RequestParam Long id) {
        partService.addProductToCart(id);
        return "redirect:/catalog";
    }

    @PostMapping("/catalog")
    public String deleteProductFromCart(@RequestParam Long id) {
        partService.deleteProductFromCart(id);
        return "redirect:/cart";
    }
}
