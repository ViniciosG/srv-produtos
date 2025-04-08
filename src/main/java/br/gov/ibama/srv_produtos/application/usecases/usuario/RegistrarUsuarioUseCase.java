package br.gov.ibama.srv_produtos.application.usecases.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.ibama.srv_produtos.api.dtos.UsuarioDTO;
import br.gov.ibama.srv_produtos.domain.entity.Usuario;
import br.gov.ibama.srv_produtos.infrastructure.persistence.repositories.UsuarioRepository;

@Service
public class RegistrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrarUsuarioUseCase(UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario execute(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByUsername(usuarioDTO.getUsername())) {
            throw new RuntimeException("Username já está em uso");
        }

        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());

        return usuarioRepository.save(usuario);
    }
}