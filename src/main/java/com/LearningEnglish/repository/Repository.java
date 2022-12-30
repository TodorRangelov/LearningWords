package com.LearningEnglish.repository;

import com.LearningEnglish.dto.ResponseDto;
import com.LearningEnglish.model.Word;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Repository {

    private List<Word> persistentRepo;
    private List<Word> temporaryRepo;
    private Random rnd;
    private boolean hasNextWord;

    public Repository() {
        this.rnd = new Random();
        this.persistentRepo = new ArrayList<>();
        this.temporaryRepo = new ArrayList<>();
    }

    public void addWords(List<Word> words) {
        hasNextWord = true;
        words.forEach(word -> {
            persistentRepo.add(word);
            temporaryRepo.add(word);
        });
    }

    public void addWord(Word word) {
        persistentRepo.add(word);
        temporaryRepo.add(word);
    }

    public ResponseDto getNextWord() {
        if (temporaryRepo.size() == 0) {
            return ResponseDto.builder()
                    .message("The Words Have Just Finished!")
                    .successful(false)
                    .build();
        }
        int index = rndIndex(temporaryRepo.size());
        return ResponseDto.builder()
                .word(temporaryRepo.remove(index))
                .successful(true)
                .build();
    }

    public void addWordsInCurrentFromInternal() {
        persistentRepo.forEach(word -> temporaryRepo.add(word));
    }

    public List<Word> getPersistentRepo() {
        return persistentRepo;
    }

    public List<Word> getTemporaryRepo() {
        return temporaryRepo;
    }

    public void removeWordByIdFromRepo(int id) {
        for (int i = 0; i < persistentRepo.size(); i++) {
            Word word = persistentRepo.get(i);
            if (word.getId() == id) {
                persistentRepo.remove(i);
                break;
            }
        }
    }

    public void removeWordByIdFromTemporaryRepo(int id) {
        for (int i = 0; i < temporaryRepo.size(); i++) {
            Word word = temporaryRepo.get(i);
            if (word.getId() == id) {
                temporaryRepo.remove(i);
                break;
            }
        }
    }

    public void removeAllWords(String typeRepo) {
        if (typeRepo.equals("Temporary")) {
            temporaryRepo.clear();
        } else {
            persistentRepo.clear();
        }
    }

    private int rndIndex(int size) {
        return rnd.nextInt(size);
    }

    public boolean hasNextWord() {
        if (temporaryRepo.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
