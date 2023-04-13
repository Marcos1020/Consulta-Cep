package com.sanches.consultacep.serviceTest;

import com.sanches.consultacep.clientInegration.Client;
import com.sanches.consultacep.controller.request.CepRequest;
import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.controller.response.ReturnIntegrationResponse;
import com.sanches.consultacep.exception.BadRequestException;
import com.sanches.consultacep.service.CepService;
import com.sanches.consultacep.utils.Constants;
import com.sanches.consultacep.utils.ValorFretePorRegiao;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CepServiceTest {

    @Mock
    private ValorFretePorRegiao valorFretePorRegiao;

    @Mock
    private Client client;

    @InjectMocks
    private CepService cepService;

    @Autowired
    public CepServiceTest(ValorFretePorRegiao valorFretePorRegiao, Client client, CepService cepService) {
        this.valorFretePorRegiao = valorFretePorRegiao;
        this.client = client;
        this.cepService = cepService;
    }

    @Test()
    public void testBuscarCepValidoComCepInvalido() {
        CepRequest cepRequest = new CepRequest("15138-226");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            cepService.buscarCepValido(cepRequest);
        });
        assertEquals(Constants.CEP_INVALIDO, exception.getMessage());
    }

    @Test
    public void testBuscaUmCepComAQuantidadeDeCaracteresMenorQueOEsperado(){
        CepRequest cepRequest = new CepRequest("1513826");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            cepService.buscarCepValido(cepRequest);
        });
        assertEquals(Constants.CEP_INVALIDO, exception.getMessage());
    }

    @Test
    public void testBuscarCepValidoComCepNaoNumerico() {
        CepRequest cepRequest = new CepRequest("15138h26");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            cepService.buscarCepValido(cepRequest);
        });
        assertEquals(Constants.CEP_INVALIDO_ADICIONE_APENAS_NUMEROS, exception.getMessage());
    }

    @Test
    void buscarCepValido_deveRetornarCepResponseValido() throws BadRequestException {

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

        CepResponse cepResponse = cepService.buscarCepValido(cepRequest);

        assertEquals(cepValido, cepResponse.getCep());
        assertEquals("Rua Domingos Tedeschi", cepResponse.getRua());
        assertEquals("", cepResponse.getComplemento());
        assertEquals("Jardim Santa Cláudia", cepResponse.getBairro());
        assertEquals("Mirassol", cepResponse.getCidade());
        assertEquals("SP", cepResponse.getEstado());
        assertEquals(valorFrete, Optional.ofNullable(cepResponse.getValorFrete()));
    }
}
