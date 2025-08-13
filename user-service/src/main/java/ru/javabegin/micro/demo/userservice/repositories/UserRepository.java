package ru.javabegin.micro.demo.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javabegin.micro.demo.userservice.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
