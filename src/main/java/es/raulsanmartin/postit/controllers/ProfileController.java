package es.raulsanmartin.postit.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import es.raulsanmartin.postit.model.Message;
import es.raulsanmartin.postit.model.MessageRepository;
import es.raulsanmartin.postit.model.User;
import es.raulsanmartin.postit.model.UserRepository;

@Controller
@RequestMapping(path = "/user")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping(path = "/{id}")
    public String profileView(@PathVariable(value="id") String userId, Model model) {
        User user = userRepository.findById(userId);
        List<Message> messages = messageRepository.findByUserAndResponseToIsNullOrderByTimestampDesc(user);

        model.addAttribute("user", user);
        model.addAttribute("messages", messages);
        return "profile";
    }
}
