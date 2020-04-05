package es.raulsanmartin.postit.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.raulsanmartin.postit.model.Message;
import es.raulsanmartin.postit.model.User;

@Controller
@RequestMapping(path = "/")
public class MainController {

    @GetMapping(path = "/")
    public String mainView(Model model) {
        List<Message> messages = new ArrayList<Message>();
        User user = new User();
        user.setId(1);
        user.setEmail("johnsmith@example.com");
        user.setName("John Smith");
        user.setNick("johnsmith");

        Message message = new Message();
        message.setId(1);
        message.setUser(user);
        message.setText("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.");
        message.setTimestamp(1586050000000L);
        messages.add(message);

        message = new Message();
        message.setId(2);
        message.setUser(user);
        message.setText("Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.");
        message.setTimestamp(1582000040000L);
        messages.add(message);

        message = new Message();
        message.setId(3);
        message.setUser(user);
        message.setText("Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur?");
        message.setTimestamp(1582000000000L);
        messages.add(message);

        user = new User();
        user.setId(2);
        user.setEmail("apaul@example.com");
        user.setName("Aaron Paul");
        user.setNick("apaul");

        message = new Message();
        message.setId(2);
        message.setUser(user);
        message.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        message.setTimestamp(1586080075000L);
        messages.add(message);

        user = new User();
        user.setId(2);
        user.setEmail("chrisevans@example.com");
        user.setName("Chris Evans");
        user.setNick("chrisevans");

        message = new Message();
        message.setId(2);
        message.setUser(user);
        message.setText("Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.");
        message.setTimestamp(1586000000000L);
        messages.add(message);

        messages.sort((e1, e2) -> new Long(e2.getTimestamp()).compareTo(new Long(e1.getTimestamp())));
        model.addAttribute("messages", messages);
        return "main_view";
    }
}
