package com.raiburst.demo.Controller;

import com.raiburst.demo.Dto.ApiResponse;
import com.raiburst.demo.Models.Account;
import com.raiburst.demo.Models.UserEntity;
import com.raiburst.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // 1. Create a new user
    @PostMapping("/users")
    public ApiResponse<UserEntity> createUser(@RequestBody UserEntity user) {
        return new ApiResponse<>(userService.createUser(user),String.valueOf(HttpStatus.OK));
    }

    // 2. Get a user by ID
    @GetMapping("/users/{userId}")
    public ApiResponse<UserEntity> getUserById(@PathVariable Long userId) {
        return new ApiResponse<>(userService.getUserById(userId),String.valueOf(HttpStatus.OK));
    }

    // 3. Update an existing user
    @PutMapping("/users/{userId}")
    public ApiResponse<UserEntity> updateUser(@PathVariable Long userId, @RequestBody UserEntity user) {
        return new ApiResponse<>(userService.updateUser(userId, user),String.valueOf(HttpStatus.OK));
    }

    // 4. Delete a user by ID
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    // 5. Create an account for a user
    @PostMapping("/users/{userId}/accounts")
    public ApiResponse<Account> createAccountForUser(@PathVariable Long userId,
                                        @RequestParam String type,
                                        @RequestParam String currency) {
        return new ApiResponse<>(userService.createAccountForUser(userId, type, currency),String.valueOf(HttpStatus.OK));
    }

    // 6. Get all accounts for a user
    @GetMapping("/users/{userId}/accounts")
    public ApiResponse<List<Account>> getAccounts(@PathVariable Long userId) {
        return new ApiResponse<>(userService.getAccounts(userId),String.valueOf(HttpStatus.OK));
    }

}
