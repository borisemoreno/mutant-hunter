package com.xmen.mutanthunter.controller;

import com.xmen.mutanthunter.model.DnaRequest;
import com.xmen.mutanthunter.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mutant")
public class MutantController {

    @Autowired
    MutantService mutantService;

    @PostMapping
    public ResponseEntity isMutant(@RequestBody DnaRequest dna) {
        return  mutantService.isMutant(dna.getDna())? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
