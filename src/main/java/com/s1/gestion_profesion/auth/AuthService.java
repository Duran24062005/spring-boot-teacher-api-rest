package com.s1.gestion_profesion.auth;

import com.s1.gestion_profesion.config.JwtService;
import com.s1.gestion_profesion.exception.BusinessRuleException;
import com.s1.gestion_profesion.model.Usuario;
import com.s1.gestion_profesion.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessRuleException("Credenciales inválidas"));

        if (!Boolean.TRUE.equals(usuario.getEnabled())) {
            throw new BusinessRuleException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new BusinessRuleException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(usuario);
        return new LoginResponse(token, usuario.getUsername(), usuario.getRole());
    }
}
