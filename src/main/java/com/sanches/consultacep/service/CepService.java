package com.sanches.consultacep.service;

import com.sanches.consultacep.clientInegration.CepIntegration;
import com.sanches.consultacep.controller.request.CepRequest;
import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.controller.response.ReturnIntegrationResponse;
import com.sanches.consultacep.exception.BadRequestException;
import com.sanches.consultacep.exception.NotFoundException;
import com.sanches.consultacep.utils.Constants;
import com.sanches.consultacep.utils.ShippingValueByRegion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Component
public class CepService {

    private CepIntegration client;
    private ShippingValueByRegion valorFretePorRegiao;

    @Autowired
    public CepService(CepIntegration client, ShippingValueByRegion valorFretePorRegiao) {
        this.client = client;
        this.valorFretePorRegiao = valorFretePorRegiao;
    }

    public CepResponse searchValidZipCode(final CepRequest cepRequest) throws BadRequestException, NotFoundException {

        int caractersCep = 8;
        if (cepRequest.getCep().length() != caractersCep) {
            log.info(Constants.CEP_INVALIDO);
            throw new BadRequestException(Constants.CEP_INVALIDO);

        } else if (!cepRequest.getCep().matches("\\d{8}")) {
            log.info(Constants.CEP_INVALIDO_ADICIONE_APENAS_NUMEROS);
            throw new BadRequestException(Constants.CEP_INVALIDO_ADICIONE_APENAS_NUMEROS);
        }

        ReturnIntegrationResponse returnIntegrationResponse = this.client.consultaCep(cepRequest.getCep());
        String convertUfToState = returnIntegrationResponse.getUf();
        double freightValue = valorFretePorRegiao.getFreightValue(convertUfToState);

        return ConvertTheDataReceivedFromTheIntegration(returnIntegrationResponse, freightValue);
    }

    private CepResponse ConvertTheDataReceivedFromTheIntegration(ReturnIntegrationResponse cepResponseIntegrationReturn, double freightValue) {
        CepResponse cepResponse = CepResponse.builder().build();
        cepResponse.setCep(cepResponseIntegrationReturn.getCep());
        cepResponse.setRua(cepResponseIntegrationReturn.getLogradouro());
        cepResponse.setComplemento(cepResponseIntegrationReturn.getComplemento());
        cepResponse.setBairro(cepResponseIntegrationReturn.getBairro());
        cepResponse.setEstado(cepResponseIntegrationReturn.getUf());
        cepResponse.setCidade(cepResponseIntegrationReturn.getLocalidade());
        cepResponse.setValorFrete(freightValue);
        return cepResponse;
    }
}