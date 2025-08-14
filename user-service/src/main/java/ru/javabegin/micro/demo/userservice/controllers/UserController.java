package ru.javabegin.micro.demo.userservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.micro.demo.userservice.dto.RegisterRequest;
import ru.javabegin.micro.demo.userservice.dto.UserResponse;
import ru.javabegin.micro.demo.userservice.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = userService.registerUser(registerRequest);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Проверка доустпности email
     */
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        Boolean available = userService.isEmailAvailable(email);
        return ResponseEntity.ok(available);
    }

    /**
     * Получение пользователя по id
     */
    public ResponseEntity<UserResponse> getUser(@RequestParam Long id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
