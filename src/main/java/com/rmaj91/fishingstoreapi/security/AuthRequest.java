package com.rmaj91.fishingstoreapi.security;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
