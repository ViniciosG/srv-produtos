package br.gov.ibama.srv_produtos.domain.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private String nome;
    private String email;
    private LocalDateTime dataHoraRegistro;
}