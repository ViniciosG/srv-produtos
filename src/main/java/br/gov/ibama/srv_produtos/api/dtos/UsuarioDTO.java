package br.gov.ibama.srv_produtos.api.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private String password;
    private String nome;
    private String email;
    private LocalDateTime dataHoraRegistro;
}