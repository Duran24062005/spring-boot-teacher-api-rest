package com.s1.gestion_profesion.auth;

import com.s1.gestion_profesion.config.JwtService;
import com.s1.gestion_profesion.exception.BusinessRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        if (request.username().equals("admin") &&
                request.password().equals("1234")) {

            String token = jwtService.generateToken(request.username());
            return Map.of("token", token);
        }

        throw new BusinessRuleException("Credenciales inválidas");
    }
}
