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
    public String createUser(@ModelAttribute User user,
                             @RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "email") String email) {

        //SAVE TO DB
        userRepository.save(new User(username, password, email));
        //RETURNS USER TO THE HOME PAGE
        return "redirect:/";
    }
}
