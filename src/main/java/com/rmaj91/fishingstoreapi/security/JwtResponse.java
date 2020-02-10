package com.rmaj91.fishingstoreapi.security;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JwtResponse {
    private String token;
    private Date expiredAt;
}
