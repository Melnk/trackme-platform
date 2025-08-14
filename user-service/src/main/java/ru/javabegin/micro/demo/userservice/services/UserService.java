package ru.javabegin.micro.demo.userservice.services;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javabegin.micro.demo.userservice.dto.RegisterRequest;
import ru.javabegin.micro.demo.userservice.dto.UserResponse;
import ru.javabegin.micro.demo.userservice.entities.User;
import ru.javabegin.micro.demo.userservice.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Проверяет, свободен ли email для регистрации
     * @param email email для проверки
     * @return true если email не занят
     * @throws IllegalArgumentException если email null/пустой
     * @throws ServiceException при ошибках доступа к БД
     */
    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        try {
            return !userRepository.existsByEmail(email);
        } catch (DataAccessException e) {
            throw new ServiceException("Database access error", e);
        }
    }

    @Transactional
    public UserResponse registerUser(RegisterRequest request) {
        if (!isEmailAvailable(request.email())) {
            throw new IllegalArgumentException("Email is already taken");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = User.builder()
                .email(request.email())
                .password(encodedPassword)
                .build();

        User savedUser = userRepository.save(user);

        return new UserResponse(savedUser.getId(), savedUser.getEmail());
    }

    public Optional<UserResponse> findUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserResponse(user.getId(), user.getEmail()));
    }
}
