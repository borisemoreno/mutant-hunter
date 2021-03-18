package com.xmen.mutanthunter.repository;

import com.xmen.mutanthunter.model.StatsDto;
import com.xmen.mutanthunter.model.db.DnaVerifications;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DnaVerificationsRepository extends CrudRepository<DnaVerifications, UUID> {
    Optional<DnaVerifications> findByDna(String dna);

    @Query(value = "select new com.xmen.mutanthunter.model.StatsDto(count(d.id), d.mutant) from DnaVerifications d group by d.mutant")
    List<StatsDto> getStats();
}
