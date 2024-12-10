package com.raiburst.demo.Controller;

import com.raiburst.demo.Dto.ApiResponse;
import com.raiburst.demo.Models.Transaction;
import com.raiburst.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Get account balance by account ID
    @GetMapping("/{userId}/accounts/{accountId}/balance")
    public ResponseEntity<ApiResponse<Double>> getAccountBalance(@PathVariable Long userId,
                                                                 @PathVariable Long accountId) {
        try {
            Double balance = userService.getAccountBalance(accountId);
            return ResponseEntity.ok(new ApiResponse<>(balance, "Account balance fetched successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), 400));
        }
    }

    // 2. Get all transactions for a user
    @GetMapping("/{userId}/transactions")
    public ResponseEntity<ApiResponse<List<Transaction>>> getTransactions(@PathVariable Long userId) {
        try {
            List<Transaction> transactions = userService.getTransactions(userId);
            return ResponseEntity.ok(new ApiResponse<>(transactions, "Transactions fetched successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), 400));
        }
    }

    // 3. Make payment
    @PostMapping("/{fromUserId}/make-payment")
    public ResponseEntity<ApiResponse<Transaction>> makePayment(@PathVariable Long fromUserId,
                                                                @RequestParam String toUserPhoneNumber,
                                                                @RequestParam double amount,
                                                                @RequestParam String currency,
                                                                @RequestParam String description) {
        try {
            Transaction transaction = userService.makePayment(fromUserId, toUserPhoneNumber, amount, currency, description);
            return ResponseEntity.ok(new ApiResponse<>(transaction, "Payment made successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), 400));
        }
    }
}