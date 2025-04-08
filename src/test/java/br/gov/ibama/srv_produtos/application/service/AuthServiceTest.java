package br.gov.ibama.srv_produtos.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.gov.ibama.srv_produtos.api.dtos.LoginDTO;
import br.gov.ibama.srv_produtos.api.dtos.UsuarioDTO;
import br.gov.ibama.srv_produtos.domain.entities.Usuario;
import br.gov.ibama.srv_produtos.infrastructure.persistence.repositories.UsuarioRepository;
import br.gov.ibama.srv_produtos.infrastructure.security.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

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
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication))
                .thenReturn("jwtToken");

        String token = authService.login(loginDTO);

        assertNotNull(token);
        assertEquals("jwtToken", token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken(authentication);
    }

    @Test
    void login_ShouldThrowException_WhenAuthenticationFails() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        assertThrows(RuntimeException.class, () -> authService.login(loginDTO));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void register_ShouldCreateUser_WhenDataIsValid() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario savedUsuario = authService.register(usuarioDTO);

        assertNotNull(savedUsuario);
        assertEquals(usuarioDTO.getUsername(), savedUsuario.getUsername());
        assertEquals("encodedPassword", savedUsuario.getPassword());
        assertEquals(usuarioDTO.getNome(), savedUsuario.getNome());
        assertEquals(usuarioDTO.getEmail(), savedUsuario.getEmail());

        verify(passwordEncoder).encode(usuarioDTO.getPassword());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void register_ShouldThrowException_WhenRepositoryFails() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> authService.register(usuarioDTO));
        verify(passwordEncoder).encode(usuarioDTO.getPassword());
        verify(usuarioRepository).save(any(Usuario.class));
    }
}