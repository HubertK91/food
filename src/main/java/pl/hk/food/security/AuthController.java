package pl.hk.food.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.hk.food.client.ClientService;

import javax.validation.Valid;

@Controller
public class AuthController {

    private final ClientService clientService;

    public AuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error,
                            Model model) {
        boolean showErrorMessage = error != null;
        model.addAttribute("showErrorMessage", showErrorMessage);
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
        String address = registerFormDto.getAddress();
        String firstName = registerFormDto.getFirstName();
        String lastName = registerFormDto.getLastName();
        clientService.registerUser(username, rawPassword, firstName, lastName, address);
        redirectAttributes.addFlashAttribute("create", registerFormDto);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Model model, @ModelAttribute(name = "create") RegisterFormDto registerFormDto){
        model.addAttribute("createAccount", registerFormDto);
        return "success";
    }
}
