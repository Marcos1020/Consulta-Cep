package com.sanches.consultacep.controller;

import com.sanches.consultacep.basePath.BasePath;
import com.sanches.consultacep.controller.request.CepRequest;
import com.sanches.consultacep.controller.response.CepResponse;
import com.sanches.consultacep.exception.BadRequestException;
import com.sanches.consultacep.exception.NotFoundException;
import com.sanches.consultacep.service.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(BasePath.BASE_PATH_URL)
public class CepController {

    private CepService cepService;
    @Autowired
    public CepController(CepService cepService){
        this.cepService = cepService;
    }

    @PostMapping
    public ResponseEntity<?> buscaCep(

            @Valid @RequestBody CepRequest cepRequest) throws BadRequestException, NotFoundException {

        final CepResponse cepResponse= this.cepService.searchValidZipCode(cepRequest);
        return new ResponseEntity<>(cepResponse, HttpStatus.OK);
    }
}
