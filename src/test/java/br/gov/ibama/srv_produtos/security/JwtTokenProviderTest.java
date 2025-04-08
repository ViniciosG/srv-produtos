package br.gov.ibama.srv_produtos.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import br.gov.ibama.srv_produtos.infrastructure.security.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private final String secretKey = "chaveSecretaParaTesteComTamanhoMinimoNecessarioParaHS512DeveSerMaiorQue512BitsParaFuncionarCorretamente";
    private final int validityInMilliseconds = 3600000; // 1 hora

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", secretKey);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", validityInMilliseconds);
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");

        String token = jwtTokenProvider.generateToken(authentication);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void validateToken_ShouldReturnTrue_ForValidToken() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        String token = jwtTokenProvider.generateToken(authentication);

        boolean isValid = jwtTokenProvider.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalse_ForInvalidToken() {
        boolean isValid = jwtTokenProvider.validateToken("invalid.token.here");

        assertFalse(isValid);
    }

    @Test
    void getUsernameFromJWT_ShouldReturnUsername_FromValidToken() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        String token = jwtTokenProvider.generateToken(authentication);

        String username = jwtTokenProvider.getUsernameFromJWT(token);

        assertEquals("testuser", username);
    }
}