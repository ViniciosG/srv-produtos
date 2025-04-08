package br.gov.ibama.srv_produtos.api.dtos;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String type = "Bearer";
}