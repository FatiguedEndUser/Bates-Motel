package dev.besharps.batesmotel.FrontEnd.About;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutMapping {

    @GetMapping
    public String About() {
        return "about";
    }
}
