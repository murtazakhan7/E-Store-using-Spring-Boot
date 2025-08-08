package com.mmk.E_Store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class LoginRequestDTO {

        @NotBlank
        private String username;

        @NotBlank
        private String password;
    }


