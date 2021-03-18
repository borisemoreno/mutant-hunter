package com.xmen.mutanthunter.service;


import com.xmen.mutanthunter.model.StatsDto;
import com.xmen.mutanthunter.model.StatsResponse;
import com.xmen.mutanthunter.model.db.DnaVerifications;
import com.xmen.mutanthunter.repository.DnaVerificationsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MutantServiceTest {

    MutantService mutantService;

    @Mock
    private DnaVerificationsRepository dnaVerificationsRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        mutantService = new MutantService(dnaVerificationsRepository);
    }

    @Test
    public void isMutant_happy_path_is_human_exists () {
        String[] dna = {"ATGCGA","CATTGC","TTATGT","AGAGGG","CGCCTA","TCACTG"};
        String stringDna = Arrays.stream(dna).collect(Collectors.joining(","));
        DnaVerifications dnaVerifications = new DnaVerifications();
        dnaVerifications.setId(UUID.randomUUID());
        dnaVerifications.setDna(stringDna);
        dnaVerifications.setMutant(true);
        when(dnaVerificationsRepository.findByDna(any())).thenReturn(Optional.of(dnaVerifications));
        boolean result = mutantService.isMutant(dna);
        Assert.assertFalse(result);
    }

    @Test
    public void isMutant_happy_path_ismutant_exists() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        String stringDna = Arrays.stream(dna).collect(Collectors.joining(","));
        DnaVerifications dnaVerifications = new DnaVerifications();
        dnaVerifications.setId(UUID.randomUUID());
        dnaVerifications.setDna(stringDna);
        dnaVerifications.setMutant(true);
        when(dnaVerificationsRepository.findByDna(any())).thenReturn(Optional.of(dnaVerifications));
        boolean result = mutantService.isMutant(dna);
        Assert.assertTrue(result);
    }

    @Test
    public void isMutant_happy_path_ismutant_not_exists() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        String stringDna = Arrays.stream(dna).collect(Collectors.joining(","));
        when(dnaVerificationsRepository.findByDna(any())).thenReturn(Optional.empty());
        DnaVerifications dnaVerifications = new DnaVerifications();
        dnaVerifications.setId(UUID.randomUUID());
        dnaVerifications.setDna(stringDna);
        dnaVerifications.setMutant(true);
        when(dnaVerificationsRepository.save(any())).thenReturn(dnaVerifications);
        boolean result = mutantService.isMutant(dna);
        Assert.assertTrue(result);
    }

    @Test
    public void getStats_happy_path() {
        List<StatsDto> statsDto = new ArrayList<>();
        statsDto.add(new StatsDto(1L,true));
        statsDto.add(new StatsDto(1L,false));

        when(dnaVerificationsRepository.getStats()).thenReturn(statsDto);
        StatsResponse result = mutantService.getStats();
        Assert.assertTrue(1L == result.getCountMutantDna());
        Assert.assertTrue(1L == result.getCountHumanDna());
        Assert.assertTrue(Float.parseFloat("1") == result.getRatio());
    }
}
