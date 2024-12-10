package com.raiburst.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "UserEntity")
public class UserEntity {
    public UserEntity() {
    }

    public UserEntity(List<String> mobileContacts, String phoneNumber, String username, String password, String email, String lastName, String name) {
        this.mobileContacts = mobileContacts;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.email = email;
        this.lastName = lastName;
        this.name = name;
    }

    public UserEntity(Long id, String name, String lastName, String email, String password, String username, String phoneNumber, List<Account> accounts, List<Post> posts, List<String> mobileContacts) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.accounts = accounts;
        this.posts = posts;
        this.mobileContacts = mobileContacts;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Unidirectional relationship
    private List<Account> accounts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Unidirectional relationship
    private List<Post> posts;

    @ElementCollection
    @CollectionTable(name = "mobile_contacts", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "mobile_number")
    private List<String> mobileContacts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<String> getMobileContacts() {
        return mobileContacts;
    }

    public void setMobileContacts(List<String> mobileContacts) {
        this.mobileContacts = mobileContacts;
    }
}

