package com.xmen.mutanthunter.service;

import com.xmen.mutanthunter.model.StatsResponse;

public interface MutantServiceInterface {
    boolean isMutant(String[] dna);
    StatsResponse getStats();
}
