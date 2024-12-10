package com.raiburst.demo.Service;

import com.raiburst.demo.Models.UserEntity;
import com.raiburst.demo.Models.Account;
import com.raiburst.demo.Models.Post;
import com.raiburst.demo.Models.Transaction;

import java.util.List;

public interface UserService {

    // User CRUD operations
    UserEntity createUser(UserEntity user);
    UserEntity getUserById(Long userId);
    UserEntity updateUser(Long userId, UserEntity user);
    void deleteUser(Long userId);
    UserEntity findUserByPhoneNumber(String phoneNumber);
    Account createAccountForUser(Long userId, String type, String currency);
    List<UserEntity> getUsersInContactsWithAccounts(Long userId);

    // Authentication
    String signIn(String username, String password);
    void signOut();

    // Posts
    List<Post> getAllPosts(Long userId);
    Post createPost(Long userId, Post post);

    // Accounts
    List<Account> getAccounts(Long userId);
    Double getAccountBalance(Long accountId);

    // Transactions
    List<Transaction> getTransactions(Long userId);
    Transaction makePayment(Long fromUserId, String toUserPhoneNumber, double amount, String currency, String description);
}
