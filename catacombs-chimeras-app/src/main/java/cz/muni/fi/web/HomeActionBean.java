package cz.muni.fi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeActionBean {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
