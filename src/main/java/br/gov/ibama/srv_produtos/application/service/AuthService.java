package br.gov.ibama.srv_produtos.application.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.ibama.srv_produtos.api.dtos.UsuarioDTO;
import br.gov.ibama.srv_produtos.domain.dto.LoginDTO;
import br.gov.ibama.srv_produtos.domain.entity.Usuario;
import br.gov.ibama.srv_produtos.infrastructure.persistence.repositories.UsuarioRepository;
import br.gov.ibama.srv_produtos.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        return jwtTokenProvider.generateToken(authentication);
    }

    public Usuario register(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        return usuarioRepository.save(usuario);
    }
}