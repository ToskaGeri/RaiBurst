package com.raiburst.demo.Controller;

import com.raiburst.demo.Dto.ApiResponse;
import com.raiburst.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<String>> signIn(@RequestParam String username, @RequestParam String password) {
        try {
            String token = userService.signIn(username, password);
            return ResponseEntity.ok(new ApiResponse<>(token, "Sign-in successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), 401));
        }
    }

    // 2. Sign Out
    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse<Void>> signOut() {
        try {
            userService.signOut();
            return ResponseEntity.ok(new ApiResponse<>(null, "Sign-out successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), 400));
        }
    }
}
