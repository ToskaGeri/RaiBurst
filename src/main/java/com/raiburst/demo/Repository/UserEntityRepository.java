package com.raiburst.demo.Repository;

import com.raiburst.demo.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    UserEntity findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM UserEntity u WHERE u.phoneNumber IN :contacts AND EXISTS (SELECT a FROM Account a WHERE a.user.id = u.id)")
    List<UserEntity> findUsersInContactsWithAccounts(@Param("contacts") List<String> contacts);

}