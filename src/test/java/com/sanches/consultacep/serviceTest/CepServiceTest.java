package com.sanches.consultacep.serviceTest;

import com.sanches.consultacep.clientInegration.CepIntegration;
import com.sanches.consultacep.controller.request.CepRequest;
import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.controller.response.ReturnIntegrationResponse;
import com.sanches.consultacep.exception.BadRequestException;
import com.sanches.consultacep.exception.NotFoundException;
import com.sanches.consultacep.service.CepService;
import com.sanches.consultacep.utils.Constants;
import com.sanches.consultacep.utils.ShippingValueByRegion;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CepServiceTest {

    @Mock
    private ShippingValueByRegion valorFretePorRegiao;

    @Mock
    private CepIntegration client;

    @InjectMocks
    private CepService cepService;

    @Autowired
    public CepServiceTest(ShippingValueByRegion valorFretePorRegiao, CepIntegration client, CepService cepService) {
        this.valorFretePorRegiao = valorFretePorRegiao;
        this.client = client;
        this.cepService = cepService;
    }

    @Test()
    public void testBuscarCepValidoComCepInvalido() {
        CepRequest cepRequest = new CepRequest("15138-226");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            cepService.searchValidZipCode(cepRequest);
        });
        assertEquals(Constants.CEP_INVALIDO, exception.getMessage());
    }

    @Test
    public void testBuscaUmCepComAQuantidadeDeCaracteresMenorQueOEsperado(){
        CepRequest cepRequest = new CepRequest("1513826");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            cepService.searchValidZipCode(cepRequest);
        });
        assertEquals(Constants.CEP_INVALIDO, exception.getMessage());
    }

    @Test
    public void testBuscarCepValidoComCepNaoNumerico() {
        CepRequest cepRequest = new CepRequest("15138h26");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            cepService.searchValidZipCode(cepRequest);
        });
        assertEquals(Constants.CEP_INVALIDO_ADICIONE_APENAS_NUMEROS, exception.getMessage());
    }

    @Test
    void buscarCepValidoEDeveRetornarCepResponseValido() throws BadRequestException, NotFoundException {

        String cepValido = "15138226";
        CepRequest cepRequest = new CepRequest();
        cepRequest.setCep(cepValido);

        ReturnIntegrationResponse returnIntegrationResponseMock = new ReturnIntegrationResponse();
        returnIntegrationResponseMock.setCep(cepValido);
        returnIntegrationResponseMock.setLogradouro("Rua Domingos Tedeschi");
        returnIntegrationResponseMock.setComplemento("");
        returnIntegrationResponseMock.setBairro("Jardim Santa Cláudia");
        returnIntegrationResponseMock.setLocalidade("Mirassol");
        returnIntegrationResponseMock.setUf("SP");
        when(client.consultaCep(cepValido)).thenReturn(returnIntegrationResponseMock);

        Optional<Double> valorFrete = Optional.of(7.85);

        CepResponse cepResponse = cepService.searchValidZipCode(cepRequest);

        assertEquals(cepValido, cepResponse.getCep());
        assertEquals(returnIntegrationResponseMock.getLogradouro(), cepResponse.getRua());
        assertEquals(returnIntegrationResponseMock.getComplemento(), cepResponse.getComplemento());
        assertEquals(returnIntegrationResponseMock.getBairro(), cepResponse.getBairro());
        assertEquals(returnIntegrationResponseMock.getLocalidade(), cepResponse.getCidade());
        assertEquals(returnIntegrationResponseMock.getUf(), cepResponse.getEstado());
        assertEquals(valorFrete, Optional.ofNullable(cepResponse.getValorFrete()));
        System.out.println("Retornando o log com as respostas validas: " + cepResponse);
    }
}
