package es.raulsanmartin.postit.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.raulsanmartin.postit.model.Message;
import es.raulsanmartin.postit.model.User;

@Controller
@RequestMapping(path = "/post")
public class PostController {

    @GetMapping(path = "/{id}")
    public String postView(@PathVariable(value="id") String postId, Model model) {
        User user = new User();
        user.setId("johnsmith");
        user.setEmail("johnsmith@example.com");
        user.setName("John Smith");
        
        Message message = new Message();
        message.setId(1);
        message.setUser(user);
        message.setText("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.");
        message.setTimestamp(1586050000000L);

        model.addAttribute("message", message);
        return "post";
    }

    @GetMapping(path = "/{id}/responses")
    public String responsesView(@PathVariable(value="id") String postId, Model model) {
        List<Message> messages = new ArrayList<Message>();
        User user = new User();
        user.setId("johnsmith");
        user.setEmail("johnsmith@example.com");
        user.setName("John Smith");
        
        Message message = new Message();
        message.setId(1);
        message.setUser(user);
        message.setText("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.");
        message.setTimestamp(1586050000000L);
        messages.add(message);

        user = new User();
        user.setId("apaul");
        user.setEmail("apaul@example.com");
        user.setName("Aaron Paul");
        
        message = new Message();
        message.setId(2);
        message.setUser(user);
        message.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        message.setTimestamp(1586080075000L);
        messages.add(message);

        user = new User();
        user.setId("chrisevans");
        user.setEmail("chrisevans@example.com");
        user.setName("Chris Evans");

        message = new Message();
        message.setId(3);
        message.setUser(user);
        message.setText("Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.");
        message.setTimestamp(1481000000000L);
        messages.add(message);

        messages.sort((e1, e2) -> Long.valueOf(e2.getTimestamp()).compareTo(Long.valueOf(e1.getTimestamp())));
        model.addAttribute("user", user);
        model.addAttribute("messages", messages);
        return "responses";
    }
}
