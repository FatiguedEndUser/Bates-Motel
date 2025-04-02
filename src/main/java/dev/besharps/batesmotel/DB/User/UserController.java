package dev.besharps.batesmotel.DB.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/find/{id}")
    User getUser(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"User not found");
        }
        return user;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/find-all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/add/points/{id}")
    int addPoints(@PathVariable Integer id, @RequestParam int amount) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"User not found");
        }
        if (amount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Amount cannot be negative");
        }
        int newAmount = user.getLoyaltyPoints() + amount;
        user.setLoyaltyPoints(newAmount);
        userRepository.save(user);
        return newAmount;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/remove/points/{id}")
    int removePoints(@PathVariable Integer id, @RequestParam int amount) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"User not found");
        }
        if (amount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Amount cannot be negative");
        }
        int newAmount = user.getLoyaltyPoints() - amount;
        if (newAmount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Returned amount cannot be negative");
        }
        user.setLoyaltyPoints(newAmount);
        userRepository.save(user);
        return newAmount;
    }

    @PutMapping("/update/{id}")
    User updateUser(@PathVariable Integer id, @RequestBody @Valid UserDetails myDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"User not found");
        }
        return userService.updateUser(user, myDetails);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/user")
    void createUser(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/user/{id}")
    void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

}
