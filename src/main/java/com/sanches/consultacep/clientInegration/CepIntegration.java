package com.sanches.consultacep.clientInegration;

import com.sanches.consultacep.basePath.BasePath;
import com.sanches.consultacep.controller.response.ReturnIntegrationResponse;
import com.sanches.consultacep.exception.BadRequestException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "consulta-cep", url = "${feign.client.config.consulta-cep.url}")
public interface CepIntegration {
    @GetMapping(value = BasePath.BASE_PATH_INTEGRATION_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    ReturnIntegrationResponse consultaCep(
            @PathVariable("cep") String cep)throws BadRequestException;
}

