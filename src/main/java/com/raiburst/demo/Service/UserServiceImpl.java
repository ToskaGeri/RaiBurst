package com.raiburst.demo.Service;

import com.raiburst.demo.Models.*;
import com.raiburst.demo.Repository.*;
import com.raiburst.demo.Utility.AccountNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private UserEntity authenticatedUser;

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserEntity updateUser(Long userId, UserEntity user) {
        UserEntity existingUser = getUserById(userId);
        existingUser.setName(user.getName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setUsername(user.getUsername());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserEntity findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Account createAccountForUser(Long userId, String type, String currency) {
        UserEntity user = getUserById(userId);
        Account account = new Account();
        AccountDetails accountDetails = new AccountDetails();

        account.setBalance(1000.0);
        accountDetails.setType(type);
        accountDetails.setCurrency(currency);

        account.setAccountDetails(accountDetails);

        // Generate a unique account number
        String accountNumber;
        do {
            accountNumber = AccountNumberGenerator.generate();
        } while (accountRepository.findByAccountNumber(accountNumber).isPresent());

        account.setAccountNumber(accountNumber);
        account.setUser(user);

        account.setAccountDetails(accountDetails);

        accountDetailsRepository.save(accountDetails);
        return accountRepository.save(account);
    }

    @Override
    public List<UserEntity> getUsersInContactsWithAccounts(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get the user's contacts
        List<String> contacts = user.getMobileContacts();

        // Fetch users in contacts with at least one account
        return userRepository.findUsersInContactsWithAccounts(contacts);
    }

    @Override
    public String signIn(String username, String password) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            authenticatedUser = userOptional.get(); // Store authenticated user
            return "AuthToken-" + System.currentTimeMillis(); // Simulate a token for simplicity
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public void signOut() {
        if (authenticatedUser == null) {
            throw new RuntimeException("No user is currently signed in");
        }
        authenticatedUser = null; // Clear the authenticated user
    }

    @Override
    public List<Post> getAllPosts(Long userId) {
        return postRepository.findAllByUserId(userId);
    }

    @Override
    public Post createPost(Long userId, Post post) {
        UserEntity user = getUserById(userId);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public List<Account> getAccounts(Long userId) {
        return accountRepository.findAllByUserId(userId);
    }

    @Override
    public Double getAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    @Override
    public List<Transaction> getTransactions(Long userId) {
        return transactionRepository.findAllByAccountId(userId);
    }

    @Override
    public Transaction makePayment(Long fromUserId, String toUserPhoneNumber, double amount, String currency, String description) {
        // Fetch the sender (fromUser)
        UserEntity fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new RuntimeException("Sender user not found"));

        // Verify recipient (toUser) is in contacts and has at least one account
        List<UserEntity> contactsWithAccounts = getUsersInContactsWithAccounts(fromUserId);
        UserEntity toUser = contactsWithAccounts.stream()
                .filter(user -> user.getPhoneNumber().equals(toUserPhoneNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Recipient not found in contacts or has no accounts"));

        // Fetch recipient's first account
        Account toUserAccount = accountRepository.findAllByUserId(toUser.getId()).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Recipient does not have any accounts"));

        // Fetch sender's first account
        Account fromUserAccount = accountRepository.findAllByUserId(fromUser.getId()).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sender does not have any accounts"));

        if (fromUserAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for the transaction");
        }

        // Create CREDIT transaction for recipient
        Transaction creditTransaction = new Transaction();
        creditTransaction.setType(TransactionType.CREDIT);
        creditTransaction.setAmount(amount);
        creditTransaction.setCurrency(currency);
        creditTransaction.setDescription(description);
        creditTransaction.setFromUser(fromUser);
        creditTransaction.setToUser(toUser);
        creditTransaction.setAccount(toUserAccount);

        // Increase recipient's account balance
        toUserAccount.setBalance(toUserAccount.getBalance() + amount);
        accountRepository.save(toUserAccount);
        transactionRepository.save(creditTransaction);

        // Create DEBIT transaction for sender
        Transaction debitTransaction = new Transaction();
        debitTransaction.setType(TransactionType.DEBIT);
        debitTransaction.setAmount(amount);
        debitTransaction.setCurrency(currency);
        debitTransaction.setDescription(description);
        debitTransaction.setFromUser(fromUser);
        debitTransaction.setToUser(toUser);
        debitTransaction.setAccount(fromUserAccount);

        // Decrease sender's account balance
        fromUserAccount.setBalance(fromUserAccount.getBalance() - amount);
        accountRepository.save(fromUserAccount);
        transactionRepository.save(debitTransaction);

        return debitTransaction; // Return the debit transaction as confirmation
    }
}
