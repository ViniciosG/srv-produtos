package br.gov.ibama.srv_produtos.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ibama.srv_produtos.api.dtos.LoginResponseDTO;
import br.gov.ibama.srv_produtos.api.dtos.UsuarioDTO;
import br.gov.ibama.srv_produtos.application.service.AuthService;
import br.gov.ibama.srv_produtos.domain.dto.LoginDTO;
import br.gov.ibama.srv_produtos.domain.entity.Usuario;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = authService.register(usuarioDTO);
        return ResponseEntity.ok(usuario);
    }
}