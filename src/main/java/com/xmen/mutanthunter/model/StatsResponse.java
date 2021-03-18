package com.xmen.mutanthunter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsResponse {
    private Long countMutantDna;
    private Long countHumanDna;
    private float ratio;
}


