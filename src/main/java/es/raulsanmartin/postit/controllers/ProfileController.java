package es.raulsanmartin.postit.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public String profileView(Principal principal, @PathVariable(value="id") String userId, Model model) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        User user = userRepository.findById(userId);
        List<Message> messages = messageRepository.findByUserAndResponseToIsNullOrderByTimestampDesc(user);

        Boolean following = false;

        if (loggedUser.getFollowing().contains(user)) {
            following = true;
        }

        model.addAttribute("user", loggedUser);
        model.addAttribute("viewUser", user);
        model.addAttribute("messages", messages);
        model.addAttribute("following", following);
        return "profile";
    }

    @GetMapping(path = "/{id}/follow", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String _follow(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return "{\"status\":\"failed\",\"error\":\"method not allowed\"}";
    }

    @PostMapping(path = "/{id}/follow", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String follow(Principal principal, @PathVariable(value="id") String userId, HttpServletResponse response) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        User toFollowUser = userRepository.findById(userId);

        try {
            if (loggedUser.equals(toFollowUser)) throw new Exception("Cannot follow yourself");
            toFollowUser.addFollower(loggedUser);
        } catch (Throwable t) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "{\"status\":\"failed\",\"error\":\""+ t.getStackTrace().toString().replace("\"", "\\\"") + "\"}";
        }

        userRepository.save(toFollowUser);

        return "{\"id\":\"" + userId + "\",\"status\":\"following\"}";
    }

    @GetMapping(path = "/{id}/unfollow", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String _unfollow(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return "{\"status\":\"failed\",\"error\":\"method not allowed\"}";
    }

    @PostMapping(path = "/{id}/unfollow", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String unfollow(Principal principal, @PathVariable(value="id") String userId, HttpServletResponse response) {
        User loggedUser = userRepository.findByEmail(principal.getName());
        User toUnFollowUser = userRepository.findById(userId);

        try {
            if (loggedUser.equals(toUnFollowUser)) throw new Exception("Cannot unfollow yourself");
            toUnFollowUser.delFollower(loggedUser);
        } catch (Throwable t) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "{\"status\":\"failed\",\"error\":\""+ t.getStackTrace().toString().replace("\"", "\\\"") + "\"}";
        }

        userRepository.save(toUnFollowUser);
        
        return "{\"id\":\"" + userId + "\",\"status\":\"not-following\"}";
    }
}
