package com.example.carrental.dto;

import jakarta.validation.constraints.*;

public class CustomerCreateRequest {
    @NotBlank public String firstName;
    @NotBlank public String lastName;
    public String phone;
    @Email public String email;
}
