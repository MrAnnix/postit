package es.raulsanmartin.postit.controllers;

import java.util.List;
import java.security.Principal;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import es.raulsanmartin.postit.model.Message;
import es.raulsanmartin.postit.model.MessageRepository;
import es.raulsanmartin.postit.model.User;
import es.raulsanmartin.postit.services.UserService;
import es.raulsanmartin.postit.model.UserRepository;

@Controller
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping(path = "/")
    public String mainView(Principal principal, Model model) {
        List<Message> messages = messageRepository.findFirst10ByResponseToIsNullOrderByTimestampDesc();
        
        User user = userRepository.findByEmail(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("messages", messages);
        return "main";
    }

    @GetMapping(path = "/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping(path = "/register")
    public String registerForm(User user) {
        return "register";
    }

    @PostMapping(path = "/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult,
                           @RequestParam String passwordRepeat) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "redirect:register?duplicate_email";
        }
        if (userRepository.findById(user.getId()) != null) {
            return "redirect:register?duplicate_username";
        }
        if (user.getPassword().equals(passwordRepeat)) {
            userService.register(user);
        } else {
            /********************************************************
            Este error no deberia ser alcanzable ya que controlamos 
            desde el lado del cliente, que las contraseñas coincidan. 
            No siendo posible el envio del formulario mientras esto 
            no se cumpla.
            ********************************************************/
            return "redirect:register?passwords_match";
        }
        return "redirect:login?registered";
    }
}
