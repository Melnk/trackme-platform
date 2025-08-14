package ru.javabegin.micro.demo.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@Email @NotBlank String email,
                              @NotBlank @Size(min = 8) String password) {

}
