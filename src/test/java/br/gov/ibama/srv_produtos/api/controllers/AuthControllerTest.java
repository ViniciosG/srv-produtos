package br.gov.ibama.srv_produtos.api.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import br.gov.ibama.srv_produtos.api.dtos.LoginDTO;
import br.gov.ibama.srv_produtos.api.dtos.LoginResponseDTO;
import br.gov.ibama.srv_produtos.api.dtos.UsuarioDTO;
import br.gov.ibama.srv_produtos.application.service.AuthService;
import br.gov.ibama.srv_produtos.domain.entities.Usuario;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private LoginDTO loginDTO;
    private UsuarioDTO usuarioDTO;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("password");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername("testuser");
        usuarioDTO.setPassword("password");
        usuarioDTO.setNome("Test User");
        usuarioDTO.setEmail("test@example.com");

        usuario = new Usuario();
        usuario.setUsername("testuser");
        usuario.setPassword("encodedPassword");
        usuario.setNome("Test User");
        usuario.setEmail("test@example.com");
    }

    @Test
    void login_ShouldReturnTokenResponse() {
        when(authService.login(any())).thenReturn("jwtToken");

        ResponseEntity<LoginResponseDTO> response = authController.login(loginDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("jwtToken", response.getBody().getToken());
    }

    @Test
    void register_ShouldReturnCreatedUser() {
        when(authService.register(any())).thenReturn(usuario);

        ResponseEntity<Usuario> response = authController.register(usuarioDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(usuario, response.getBody());
    }
}