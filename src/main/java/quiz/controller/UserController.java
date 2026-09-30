package quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quiz.model.User;
import quiz.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            if (user.getRole() == null || user.getRole().trim().isEmpty()) {
                user.setRole("USER");
            }
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("message", "Login successful! Welcome, " + user.getUsername());
            response.put("username", user.getUsername());
            response.put("role", user.getRole() != null ? user.getRole().toUpperCase() : "USER");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}