package dev.besharps.batesmotel.FrontEnd.Home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class HomeAdapter {

    public String HomeAdapter() {
        return "HomePage";
    }
}
