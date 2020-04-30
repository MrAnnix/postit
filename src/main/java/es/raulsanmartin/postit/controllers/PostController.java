package es.raulsanmartin.postit.controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import java.util.Date;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.raulsanmartin.postit.model.Message;
import es.raulsanmartin.postit.model.MessageRepository;
import es.raulsanmartin.postit.model.User;
import es.raulsanmartin.postit.model.UserRepository;

@Controller
@RequestMapping(path = "/post")
public class PostController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping(path = "/{id}")
    public String postView(@PathVariable(value="id") int postId, Principal principal, Model model) {
        Optional<Message> message = messageRepository.findById(postId);
        if (!message.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }

        User user = userRepository.findByEmail(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("message", message.get());
        return "post";
    }

    @GetMapping(path = "/{id}/responses")
    public String responsesView(@PathVariable(value="id") int postId, Model model) {
        Optional<Message> message = messageRepository.findById(postId);
        if (!message.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }

        List<Message> messages = messageRepository.findByResponseToOrderByTimestampAsc(message);

        model.addAttribute("messages", messages);
        return "responses";
    }

    @PostMapping(path = "")
    public String postMessage(@ModelAttribute Message message, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        message.setUser(user);
        message.setTimestamp((new Date()).getTime());
        messageRepository.save(message);
        return "redirect:post/" + message.getId();
    }
}
