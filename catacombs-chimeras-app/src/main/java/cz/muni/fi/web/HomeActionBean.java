package cz.muni.fi.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeActionBean {

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
}
