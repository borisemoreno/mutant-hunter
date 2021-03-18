package com.xmen.mutanthunter.controller;

import com.xmen.mutanthunter.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/stats")
public class StatsController {
    @Autowired
    MutantService mutantService;

    @GetMapping
    public ResponseEntity getStat() {
        return  ResponseEntity.ok(mutantService.getStats());
    }
}
