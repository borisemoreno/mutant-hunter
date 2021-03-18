package com.xmen.mutanthunter.service;

import com.xmen.mutanthunter.model.StatsDto;
import com.xmen.mutanthunter.model.StatsResponse;
import com.xmen.mutanthunter.model.db.DnaVerifications;
import com.xmen.mutanthunter.repository.DnaVerificationsRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MutantService implements MutantServiceInterface {

    DnaVerificationsRepository dnaVerificationsRepository;

    public MutantService(DnaVerificationsRepository dnaVerificationsRepository) {
        this.dnaVerificationsRepository = dnaVerificationsRepository;
    }

    /**
     * Devuelve si es un dna de mutante o de humano
     *
     * @param dna Arreglo de cadenas que representa el dna de un mutante o humano.
     * @return verdadero si es mutanto o falso si es humano
     */
    @Override
    public boolean isMutant(String[] dna) {
        boolean isMutant = false;
        if (dna.length >= 4 && isSquare(dna)) {
            isMutant = getWords(dna).stream().filter(s -> (s.contains("AAAA") || s.contains("CCCC") || s.contains("GGGG") || s.contains("TTTT"))).count() > 1;
        }
        storeDnaResult(dna, isMutant);

        return isMutant;
    }

    /**
     * Devuelve las posibles conbinaciones de letras basado en las horizontales, verticales y diagonales.
     *
     * @param dna arreglo de cadenas que representa el dna de un humano o mutante.
     * @return listo de cadenas de todas las horizontales, vertiales y diagonales.
     */
    private List<String> getWords(String[] dna) {
        List<String> words = Arrays.asList(dna);
        List<String> wordsList = words;
        wordsList = Stream.concat(wordsList.stream(), getVerticals(words).stream()).collect(Collectors.toList());

        wordsList = Stream.concat(wordsList.stream(), getDiagonals(words).stream()).collect(Collectors.toList());
        Collections.reverse(words);
        wordsList = Stream.concat(wordsList.stream(), getDiagonals(words).stream()).collect(Collectors.toList());
        return wordsList;
    }

    /**
     * Devuelve las verticales que se pueden formar con el dna dado.
     *
     * @param dna lista de cadenas que representa un dna.
     * @return posibles verticales que forma el dna al organizarlo por lineas de acuerdo al order de la lista.
     */
    private List<String> getVerticals(List<String> dna) {
        List<String> newWords = new ArrayList<>();
        for (int i = 0; i < dna.size(); i++) {
            int finalI = i;
            newWords.add(dna.stream().map(s -> s.substring(finalI, finalI + 1)).collect(Collectors.joining()));
        }
        return newWords;
    }

    /**
     * Devuelve las diagonales que se pueden formar con el dna dado.
     *
     * @param dna lista de cadenas que representa un dna.
     * @return posibles conbinaciones de caracteres que se pueden formar de la lista dada al organizarla como una matriz.
     */
    private List<String> getDiagonals(List<String> dna) {
        Map<Integer, List<Character>> map = new HashMap<>();
        int row = 0;
        for (String s : dna) {
            row++;
            Integer index = row;
            for (Character c : s.toCharArray()) {
                List<Character> charList = map.containsKey(index) ? map.get(index) : new ArrayList<>();
                charList.add(c);
                map.put(index, charList);
                index++;
            }
        }
        return map.values().stream().map(characters -> characters.stream().map(String::valueOf).collect(Collectors.joining())).collect(Collectors.toList());
    }

    /**
     * Determina si el numero de registros del arreglo es igual al numero de caracteres de cada cadena.
     *
     * @param dna Arreglo de cadenas que representa el dna.
     * @return verdadero si el numero de registros en el arreglo es igual al numero de caracteres de cada cadena.
     */
    private boolean isSquare(String[] dna) {
        return dna.length == Arrays.stream(dna).filter(s -> s.length() == dna.length).count();
    }

    private void storeDnaResult(String[] dna, boolean isMutant) {
        String dnaString = Arrays.stream(dna).collect(Collectors.joining(","));
        Optional<DnaVerifications> existsDna = dnaVerificationsRepository.findByDna(dnaString);
        if (!existsDna.isPresent()) {
            DnaVerifications dnaVerifications = new DnaVerifications();
            dnaVerifications.setId(UUID.randomUUID());
            dnaVerifications.setDna(dnaString);
            dnaVerifications.setMutant(isMutant);
            dnaVerificationsRepository.save(dnaVerifications);
        }
    }

    /**
     * Returns the statustics of mutants over humans.
     * @return
     */
    public StatsResponse getStats() {
        List<StatsDto> statsDto = dnaVerificationsRepository.getStats();
        Long mutants = statsDto.stream().filter(r -> r.isMutants()).collect(Collectors.counting());
        Long humans = statsDto.stream().filter(r -> !r.isMutants()).collect(Collectors.counting());
        return new StatsResponse(
                mutants,
                humans,
                humans == 0 ? 0 : mutants / humans);
    }
}
