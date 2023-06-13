package com.sanches.consultacep.service;

import com.sanches.consultacep.clientInegration.CepIntegration;
import com.sanches.consultacep.controller.request.CepRequest;
import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.controller.response.ReturnIntegrationResponse;
import com.sanches.consultacep.exception.BadRequestException;
import com.sanches.consultacep.exception.NotFoundException;
import com.sanches.consultacep.utils.Constants;
import com.sanches.consultacep.utils.ValorFretePorRegiao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Component
public class CepService {

    private CepIntegration client;
    private ValorFretePorRegiao valorFretePorRegiao;

    @Autowired
    public CepService(CepIntegration client, ValorFretePorRegiao valorFretePorRegiao) {
        this.client = client;
        this.valorFretePorRegiao = valorFretePorRegiao;
    }

    public CepResponse buscarCepValido(final CepRequest cepRequest) throws BadRequestException, NotFoundException {

        int caractersCep = 8;
        if (cepRequest.getCep().length() != caractersCep) {
            log.info(Constants.CEP_INVALIDO);
            throw new BadRequestException(Constants.CEP_INVALIDO);

        }else if (!cepRequest.getCep().matches("\\d{8}")) {
            log.info(Constants.CEP_INVALIDO_ADICIONE_APENAS_NUMEROS);
            throw new BadRequestException(Constants.CEP_INVALIDO_ADICIONE_APENAS_NUMEROS);
        }

        ReturnIntegrationResponse returnIntegrationResponse = this.client.consultaCep(cepRequest.getCep());
        String convertUfParaEstado = returnIntegrationResponse.getUf();
        double valorFrete = valorFretePorRegiao.getValorFrete(convertUfParaEstado);

        return ConverteOsDadosRecebidosDaIntegração(returnIntegrationResponse, valorFrete);
    }

    private CepResponse ConverteOsDadosRecebidosDaIntegração(ReturnIntegrationResponse cepResponseReturn, double valorFrete) {
        CepResponse cepResponse = CepResponse.builder().build();
        cepResponse.setCep(cepResponseReturn.getCep());
        cepResponse.setRua(cepResponseReturn.getLogradouro());
        cepResponse.setComplemento(cepResponseReturn.getComplemento());
        cepResponse.setBairro(cepResponseReturn.getBairro());
        cepResponse.setEstado(cepResponseReturn.getUf());
        cepResponse.setCidade(cepResponseReturn.getLocalidade());
        cepResponse.setValorFrete(valorFrete);
        return cepResponse;
    }
}