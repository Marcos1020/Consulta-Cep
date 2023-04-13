package com.sanches.consultacep.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CepResponse {

    String cep;

    String rua;

    String complemento;

    String bairro;

    String cidade;

    String estado;

    Double valorFrete;
}
