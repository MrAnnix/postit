package es.raulsanmartin.postit.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
        Optional<Message> _message = messageRepository.findById(postId);
        if (!_message.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }

        Message message = _message.get();

        // Mensaje del cual es (puede ser) respuesta
        Message original = message.getResponseTo();

        User user = userRepository.findByEmail(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("message", message);
        model.addAttribute("original", original);
        return "post";
    }

    @GetMapping(path = "/{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String _like(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return "{\"status\":\"failed\",\"error\":\"method not allowed\"}";
    }

    @PostMapping(path = "/{id}/like")
    @ResponseBody
    public String like(@PathVariable(value="id") int postId, Principal principal, HttpServletResponse response) {
        Optional<Message> _message = messageRepository.findById(postId);
        if (!_message.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "{\"status\":\"failed\",\"error\":\"Message not found\"}";
        }

        User user = userRepository.findByEmail(principal.getName());
        Message message = _message.get();

        try {
            message.addLike(user);
        } catch (Throwable t) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "{\"status\":\"failed\",\"error\":\""+ t.getStackTrace().toString().replace("\"", "\\\"") + "\"}";
        }

        messageRepository.save(message);

        return "{\"id\":\"" + message.getId() + "\",\"status\":\"liked\"}";
    }

    @GetMapping(path = "/{id}/unlike", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String _unlike(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return "{\"status\":\"failed\",\"error\":\"method not allowed\"}";
    }

    @PostMapping(path = "/{id}/unlike")
    @ResponseBody
    public String unlike(@PathVariable(value="id") int postId, Principal principal, HttpServletResponse response) {
        Optional<Message> _message = messageRepository.findById(postId);
        if (!_message.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "{\"status\":\"failed\",\"error\":\"Message not found\"}";
        }

        User user = userRepository.findByEmail(principal.getName());
        Message message = _message.get();

        try {
            message.delLike(user);
        } catch (Throwable t) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "{\"status\":\"failed\",\"error\":\""+ t.getStackTrace().toString().replace("\"", "\\\"") + "\"}";
        }

        messageRepository.save(message);

        return "{\"id\":\"" + message.getId() + "\",\"status\":\"not-liked\"}";
    }

    @GetMapping(path = "/{id}/responses")
    public String responsesView(@PathVariable(value="id") int postId, Model model) {
        Optional<Message> message = messageRepository.findById(postId);
        if (!message.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }

        List<Message> messages = messageRepository.findByResponseToOrderByTimestampAsc(message.get());

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
