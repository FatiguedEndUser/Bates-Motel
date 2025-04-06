package dev.besharps.batesmotel.FrontEnd.Signup;

import dev.besharps.batesmotel.DB.User.User;
import dev.besharps.batesmotel.DB.User.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignupMapping {
    private final UserRepository userRepository;

    public SignupMapping(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String Signup(Model model) {
        model.addAttribute("newUser", new User());
        return "SignUp";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user, Model model) {
        //SAVE TOO DB
        userRepository.save(user);
        //RETURNS USER TO THE HOME PAGE
        return "redirect:/";
    }
}
