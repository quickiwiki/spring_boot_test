package longpoll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.*;

@Controller
public class LongPollServerController {
 
    /*@GetMapping(path = "/demoWithPush")
    public String demoWithPush(PushBuilder pushBuilder) {
        if (null != pushBuilder) {
            pushBuilder.path("resources/logo.png")
              .addHeader("Content-Type", "image/png")
              .push();
        }
        return "demo";
    }
 
    @GetMapping(path = "/demoWithoutPush")
    public String demoWithoutPush() {
        return "demo";
    }*/
}