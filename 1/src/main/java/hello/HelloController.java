package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/plot")
    public String plot()
    {
        return "plot.html";
    }


}