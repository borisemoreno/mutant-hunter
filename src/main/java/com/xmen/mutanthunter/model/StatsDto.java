package com.xmen.mutanthunter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsDto {
    private Long counts;
    private boolean mutants;
}
