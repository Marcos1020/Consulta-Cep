package com.sanches.consultacep.converções;

import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.controller.response.ReturnIntegrationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Conversoes {

    public CepResponse ConverteOsDadosRecebidosDaIntegração(ReturnIntegrationResponse cepResponse, double valorFrete) {
        CepResponse cepResponseReturn = new CepResponse();
        cepResponseReturn.setCep(cepResponse.getCep());
        cepResponseReturn.setRua(cepResponse.getLogradouro());
        cepResponseReturn.setComplemento(cepResponse.getComplemento());
        cepResponseReturn.setBairro(cepResponse.getBairro());
        cepResponseReturn.setEstado(cepResponse.getUf());
        cepResponseReturn.setCidade(cepResponse.getLocalidade());
        cepResponseReturn.setValorFrete(valorFrete);
        return cepResponseReturn;
    }
}