package dev.besharps.batesmotel.FrontEnd.Login;

import dev.besharps.batesmotel.DB.User.User;
import dev.besharps.batesmotel.DB.User.UserController;
import dev.besharps.batesmotel.DB.User.UserRepository;
import dev.besharps.batesmotel.DB.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginMapping {
    private final UserController userController;

    //TODO LOGIN IS LOOKING FOR THE USER RIGHT AWAY, WE NEED THE SUBMIT BUTTON TO CALL SIGNIN
    @GetMapping
    public String Login(Model model,
                        HttpServletRequest request,
                        Boolean loggedIn,
                        @RequestParam(required = false, name = "email") String email,
                        @RequestParam(required = false, name = "password") String password) {

        //PERSISTENT SESSION
        HttpSession session = request.getSession();
        //CALLING SIGN IN
        if(SignIn(email, password)){
            session.setAttribute("loggedIn", loggedIn);
        }

        //ADDING SESSION TO MODEL
        model.addAttribute("loggedIn", loggedIn);


        //ADDING EMAIL AND PASSWORD ATTRIBUTES ATTRIBUTES
        model.addAttribute("email", email);
        model.addAttribute("password", password);

       return "Login";
    }

    public Boolean SignIn(
                        @RequestParam(name = "email") String email,
                        @RequestParam(name = "password") String password) {

        //GRABBING USER EMAIL IF EXIST IN THE DATABASE
        String userEmail = userController.getUserByEmail(email).getEmail();
        //GRABBING USER PASSWORD (WILL HARDEN SECURITY AT A LATER DATE)
        String userPassword = userController.getUserByEmail(email).getPassword();

        //RETURNS USER EMAIL AND PASSWORD
        //BASIC VALIDATION THAT USER EXIST
        return userEmail.equals(email) && userPassword.equals(password);
    }
}
