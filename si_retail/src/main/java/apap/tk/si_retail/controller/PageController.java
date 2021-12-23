package apap.tk.si_retail.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public String home(){
        return "landing-page";
    }

    @RequestMapping("/login")
    public String login(){ return "login-page"; }
}