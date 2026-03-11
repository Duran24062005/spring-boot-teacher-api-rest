package com.s1.gestion_profesion.auth;

public record LoginResponse(String token, String username, String role) {
}
