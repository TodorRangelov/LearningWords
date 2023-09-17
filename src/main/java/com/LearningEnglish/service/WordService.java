package com.LearningEnglish.service;

import com.LearningEnglish.dto.AddWordsDto;
import com.LearningEnglish.dto.RequestDto;
import com.LearningEnglish.dto.ResponseDto;
import com.LearningEnglish.model.Word;
import com.LearningEnglish.repository.Repository;
import com.LearningEnglish.repository.WordRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private TemplateEngine templateEngine;
    private Random rnd;
    private Word currentWord;
    private final Repository repository;
    private final HTMLService htmlCreator;
    private Boolean useInternalRepository;
    private boolean toEnglish;
    private List<Word> allWord;

    public WordService(WordRepository wordRepository, TemplateEngine templateEngine,
                       Repository repository, HTMLService htmlCreator) {
        this.wordRepository = wordRepository;
        this.templateEngine = templateEngine;
        this.repository = repository;
        this.htmlCreator = htmlCreator;
        this.rnd = new Random();
        this.useInternalRepository = false;
    }

    public void addWords(AddWordsDto words) {

        List<String> newWordsAsString = List.of(words.getWords().split("@"));
        List<Word> wordList = new ArrayList<>();

        for (String newWord : newWordsAsString) {
            String trim = newWord.trim();
            String[] split = trim.split("\t");
            Word word;
            if (split[0].equals("български")) {
                word = new Word(split[3], split[2]);
            } else {
                word = new Word(split[2], split[3]);
            }
            wordList.add(word);
        }
        wordRepository.saveAll(wordList);
    }

    public List<Word> getAllWord() {
        return wordRepository.findAll();
    }

    public ResponseDto getRandomWord() {
        ResponseDto response;
        if (useInternalRepository) {
            response = getWordFromInternalRepo();
            if (response.isSuccessful()) {
                currentWord = response.getWord();
            }
        } else {
            if (allWord == null) {
                allWord = getAllWord();
            }
            currentWord = allWord.get(rndIndex(allWord.size()));
            response = ResponseDto.builder()
                    .word(currentWord)
                    .successful(true)
                    .build();
        }
        if (response.isSuccessful()) {
            updateWord(currentWord);
        }
        return response;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    public ResponseDto handleAnswer(Word wordDto) {
        if (wordDto.getToBulgarian() == null || wordDto.getToBulgarian().isBlank()) {
            return handleWordComparisonТоEnglish(wordDto);
        } else {
            return handleWordComparisonТоBulgarian(wordDto);
        }
    }

    public ResponseDto handleWordComparisonТоBulgarian(Word wordDto) {
        ResponseDto responseDto = new ResponseDto();
        if (!wordDto.getToBulgarian().toLowerCase().equals(currentWord.getToBulgarian().toLowerCase())) {
            currentWord.setMistake(currentWord.getMistake() + 1);
            wordRepository.save(currentWord);
            responseDto.setMessage(String.format("Wrong answer! Word: %s  -  %s  /  %s",
                    currentWord.getToEnglish(),
                    currentWord.getToBulgarian(),
                    wordDto.getToBulgarian()));
            responseDto.setSuccessful(false);
        } else {
            responseDto.setMessage("Right answer!");
            responseDto.setSuccessful(true);
        }
        return responseDto;
    }

    public ResponseDto handleWordComparisonТоEnglish(Word wordDto) {
        ResponseDto responseDto = new ResponseDto();
        if (!wordDto.getToEnglish().equals(currentWord.getToEnglish().toLowerCase())) {
            currentWord.setMistake(currentWord.getMistake() + 1);
            wordRepository.save(currentWord);
            responseDto.setMessage(String.format("Wrong answer! Word: %s  -  %s  /  %s",
                    currentWord.getToEnglish(),
                    currentWord.getToBulgarian(),
                    wordDto.getToEnglish()));
            responseDto.setSuccessful(false);
        } else {
            responseDto.setMessage("Right answer!");
            responseDto.setSuccessful(true);
        }
        return responseDto;
    }

    public ResponseDto search(Word request) {
        ResponseDto response = new ResponseDto();
        if (!request.getToEnglish().isBlank()) {
            response.setWords(wordRepository.findByToEnglish(request.getToEnglish()));
        } else if (!request.getToBulgarian().isBlank()) {
            response.setWords(wordRepository.findByToBulgarian(request.getToBulgarian()));
        } else {
            response.setWord(new Word("Wrong input!", "Грешни входни данни!"));
            response.setSuccessful(false);
        }
        if (response.getWords() == null || response.getWords().isEmpty()) {
            response.setWord(new Word("There isn't find word: " + request.getToEnglish() + request.getToBulgarian(), " "));
            response.setSuccessful(false);
        } else {
            response.setSuccessful(true);
            currentWord = response.getWords().get(0);
        }
        return response;
    }

    public List<Word> getLastWords(int number) {
        useInternalRepository = true;
        List<Word> wordsAfterAndNumber = wordRepository.findWordsOffsetAndLimit(0, number);
        repository.addWords(wordsAfterAndNumber);
        return wordsAfterAndNumber;
    }

    public ResponseDto getWordFromInternalRepo() {
        if (!repository.hasNextWord()) {
            useInternalRepository = false;
        }
        return repository.getNextWord();
    }

    public List<Word> numberOfWords(RequestDto requestDto) {
        useInternalRepository = true;
        int numberOfWords = Integer.parseInt(requestDto.getRequest());
        return getLastWords(numberOfWords);
    }

    public void addWordInInternalRepository() {
        repository.addWord(currentWord);
    }

    public void editCurrentWord(Word request) {
        currentWord.setToEnglish(request.getToEnglish());
        currentWord.setToBulgarian(request.getToBulgarian());
        wordRepository.save(currentWord);
    }

    private int rndIndex(int size) {
        return rnd.nextInt(size);
    }

    public void setUseInternalRepository(Boolean useInternalRepository) {
        this.useInternalRepository = useInternalRepository;
    }

    private void updateWord(Word word) {
        word.setViews(word.getViews() + 1);
        wordRepository.save(word);
    }

    public boolean isToEnglish() {
        return toEnglish;
    }

    public void setToEnglish(boolean toEnglish) {
        this.toEnglish = toEnglish;
    }

}


