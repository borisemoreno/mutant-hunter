package com.xmen.mutanthunter.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MutantService implements MutantServiceInterface {

    @Override
    public boolean isMutant(String[] dna) {
        boolean isMutant = false;
        if (dna.length>=4 && isSquare(dna)) {
            isMutant = getWords(dna).stream().filter(s -> (s.contains("AAAA") || s.contains("CCCC") || s.contains("GGGG") || s.contains("TTTT"))).count()>1;
        }
        return isMutant;
    }

    private List<String> getWords(String[] dna) {
        List<String> words = Arrays.asList(dna);
        List<String> wordsList = words;
        wordsList = Stream.concat(wordsList.stream(),getVerticals(words).stream()).collect(Collectors.toList());

        wordsList = Stream.concat(wordsList.stream(),getDiagonals(words).stream()).collect(Collectors.toList());
        Collections.reverse(words);
        wordsList = Stream.concat(wordsList.stream(),getDiagonals(words).stream()).collect(Collectors.toList());
        return wordsList;
    }

    private List<String> getVerticals (List<String> dna) {
        List<String> newWords = new ArrayList<>();
        for (int i = 0; i<dna.size();i++) {
            int finalI = i;
            newWords.add(dna.stream().map(s -> s.substring(finalI,finalI+1)).collect(Collectors.joining()));
        }
        return newWords;
    }

    private List<String> getDiagonals (List<String> dna ) {
        Map<Integer, List<Character>> map = new HashMap<>();
        int row = 0;
        for (String s : dna) {
            row++;
            Integer index = row;
            for (Character c: s.toCharArray()) {
                List<Character> charList = map.containsKey(index) ? map.get(index) : new ArrayList<>();
                charList.add(c);
                map.put(index,charList);
                index++;
            }
        }
        return  map.values().stream().map(characters -> characters.stream().map(String::valueOf).collect(Collectors.joining())).collect(Collectors.toList());
    }

    private boolean isSquare(String[] dna) {
        return dna.length == Arrays.stream(dna).filter(s -> s.length() == dna.length).count();
    }
}
