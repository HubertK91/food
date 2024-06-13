package pl.hk.food.security;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.hk.food.client.ClientService;
import pl.hk.food.restaurant.RestaurantService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthController {

    private final ClientService clientService;

    private final RestaurantService restaurantService;

    public AuthController(ClientService clientService, RestaurantService restaurantService) {
        this.clientService = clientService;
        this.restaurantService = restaurantService;
    }


    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error, @RequestParam(required = false) String loginSuccess,
                            Model model) {
        boolean showErrorMessage = error != null;
        boolean showSuccessMessage = loginSuccess != null;
        model.addAttribute("showErrorMessage", showErrorMessage);
        model.addAttribute("showSuccessMessage", showSuccessMessage);
        return "securityForms/loginForm";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "securityForms/loginSuccess";
    }


    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("createAccount", new RegisterFormDto());
        return "securityForms/registrationForm";
    }

    @PostMapping("/registration")
    public String register(Model model, @Valid @ModelAttribute("createAccount") RegisterFormDto registerFormDto,
                           BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            model.addAttribute("createAccount", registerFormDto);
            return "securityForms/registrationForm";
        }
        String username = registerFormDto.getUsername();
        String rawPassword = registerFormDto.getPassword();
        String city = registerFormDto.getCity();
        String phone = registerFormDto.getPhone();
        String email = registerFormDto.getEmail();
        String streetAddress = registerFormDto.getStreetAddress();
        String firstName = registerFormDto.getFirstName();
        String lastName = registerFormDto.getLastName();
        clientService.registerUser(username, firstName, lastName, rawPassword, city, email, phone, streetAddress);
        redirectAttributes.addFlashAttribute("create", registerFormDto);
        return "redirect:/success";
    }

    @GetMapping("/addRestaurant")
    public String registerRestaurant(Model model) {
        model.addAttribute("createRestaurant", new RegisterFormRestaurantDto());
        return "securityForms/registrationRestaurantForm";
    }

    @PostMapping("/addRestaurant")
    public String registerRestaurant(Model model, @Valid @ModelAttribute("createRestaurant") RegisterFormRestaurantDto registerFormRestaurantDto,
                           BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            model.addAttribute("createRestaurant", registerFormRestaurantDto);
            return "securityForms/registrationRestaurantForm";
        }
        String username = registerFormRestaurantDto.getUsername();
        String rawPassword = registerFormRestaurantDto.getPassword();
        String city = registerFormRestaurantDto.getCity();
        String phone = registerFormRestaurantDto.getPhone();
        String email = registerFormRestaurantDto.getEmail();
        String streetAddress = registerFormRestaurantDto.getStreetAddress();
        String name = registerFormRestaurantDto.getName();
        restaurantService.registerRestaurant(username, name, rawPassword, city, streetAddress, phone, email);
        redirectAttributes.addFlashAttribute("create", registerFormRestaurantDto);
        return "redirect:/successRestaurantRegistration";
    }

    @GetMapping("/success")
    public String success(Model model, @ModelAttribute(name = "create") RegisterFormDto registerFormDto){
        model.addAttribute("createAccount", registerFormDto);
        return "main/success";
    }

    @GetMapping("/successRestaurantRegistration")
    public String success(Model model, @ModelAttribute(name = "create") RegisterFormRestaurantDto registerFormRestaurantDto){
        model.addAttribute("createAccount", registerFormRestaurantDto);
        return "main/successRestaurantRegistration";
    }
}
