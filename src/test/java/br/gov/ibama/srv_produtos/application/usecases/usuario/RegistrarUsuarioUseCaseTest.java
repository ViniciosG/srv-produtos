package br.gov.ibama.srv_produtos.application.usecases.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.gov.ibama.srv_produtos.api.dtos.UsuarioDTO;
import br.gov.ibama.srv_produtos.domain.entities.Usuario;
import br.gov.ibama.srv_produtos.infrastructure.persistence.repositories.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class RegistrarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrarUsuarioUseCase registrarUsuarioUseCase;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername("testuser");
        usuarioDTO.setPassword("password");
        usuarioDTO.setNome("Test User");
        usuarioDTO.setEmail("test@example.com");
    }

    @Test
    void execute_ShouldCreateUser_WhenUsernameAndEmailAreAvailable() {

        when(usuarioRepository.existsByUsername(any())).thenReturn(false);
        when(usuarioRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Usuario usuario = registrarUsuarioUseCase.execute(usuarioDTO);

        // Assert
        assertNotNull(usuario);
        assertEquals(usuarioDTO.getUsername(), usuario.getUsername());
        assertEquals("encodedPassword", usuario.getPassword());
        assertEquals(usuarioDTO.getNome(), usuario.getNome());
        assertEquals(usuarioDTO.getEmail(), usuario.getEmail());

        verify(usuarioRepository).existsByUsername(usuarioDTO.getUsername());
        verify(usuarioRepository).existsByEmail(usuarioDTO.getEmail());
        verify(passwordEncoder).encode(usuarioDTO.getPassword());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void execute_ShouldThrowException_WhenUsernameAlreadyExists() {

        when(usuarioRepository.existsByUsername(any())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> registrarUsuarioUseCase.execute(usuarioDTO));
        verify(usuarioRepository).existsByUsername(usuarioDTO.getUsername());
        verify(usuarioRepository, never()).existsByEmail(any());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void execute_ShouldThrowException_WhenEmailAlreadyExists() {

        when(usuarioRepository.existsByUsername(any())).thenReturn(false);
        when(usuarioRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> registrarUsuarioUseCase.execute(usuarioDTO));
        verify(usuarioRepository).existsByUsername(usuarioDTO.getUsername());
        verify(usuarioRepository).existsByEmail(usuarioDTO.getEmail());
        verify(usuarioRepository, never()).save(any());
    }
}