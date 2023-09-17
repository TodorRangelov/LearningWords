package com.LearningEnglish.controller;

import com.LearningEnglish.dto.RequestDto;
import com.LearningEnglish.dto.ResponseDto;
import com.LearningEnglish.model.Word;
import com.LearningEnglish.repository.Repository;
import com.LearningEnglish.service.WordService;
import com.LearningEnglish.service.HTMLService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class InternalRepoController {

    private final Repository repository;
    private final HTMLService htmlCreator;
    private final WordService wordService;

    public InternalRepoController(Repository repository, HTMLService htmlCreator, WordService wordService) {
        this.repository = repository;
        this.htmlCreator = htmlCreator;
        this.wordService = wordService;
    }


    @RequestMapping(value = "/addWordInInternalRepository", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String addWordInInternalRepository() {
        wordService.addWordInInternalRepository();
        Word word = wordService.getRandomWord().getWord();
        return htmlCreator.createGetPage(wordService.isToEnglish(), word);
    }

    @RequestMapping(value = "/removeFromPersistentRepo/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String removeFromPersistentRepo(@PathVariable("id") int id) {
        repository.removeWordByIdFromRepo(id);
        return htmlCreator.createPageListOfWordsWithRemoveB(repository.getPersistentRepo(), "Persistent");
    }

    @RequestMapping(value = "/removeFromTemporaryRepo/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String removeFromTemporaryRepo(@PathVariable("id") int id) {
        repository.removeWordByIdFromTemporaryRepo(id);
        return htmlCreator.createPageListOfWordsWithRemoveB(repository.getTemporaryRepo(), "Temporary");
    }

    @RequestMapping(value = "/numberOfWords", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String numberOfWords(@ModelAttribute RequestDto request) {
        return htmlCreator.createPageListOfWordsWithRemoveB(wordService.numberOfWords(request), "Temporary");
    }

    @RequestMapping(value = "/showInternalRepo", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String showInternalRepo() {
        return htmlCreator.createInternalRepoPage();
    }

    @RequestMapping(value = "/showPersistent", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String showPersistent() {
        return htmlCreator.createPageListOfWordsWithRemoveB(repository.getPersistentRepo(), "Persistent");
    }

    @RequestMapping(value = "/showTemporary", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String showTemporary() {
        return htmlCreator.createPageListOfWordsWithRemoveB(repository.getTemporaryRepo(), "Temporary");
    }

    @RequestMapping(value = "/getAddedWords", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String addedWords() {
        if (!repository.hasNextWord()) {
            repository.addWordsInCurrentFromInternal();
        }
        wordService.setUseInternalRepository(true);
        ResponseDto response = wordService.getRandomWord();
        return htmlCreator.createGetPage(wordService.isToEnglish(), response.getWord());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String edit() {
        Word currentWord = wordService.getCurrentWord();
        return htmlCreator.createEditPage(currentWord);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String editRequest(@ModelAttribute Word request) {
        wordService.editCurrentWord(request);
        return htmlCreator.createEditSuccessPage(wordService.getCurrentWord());
    }

    @RequestMapping(value = "/removeAll/{typeRepo}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String removeAllTemporary(@PathVariable("typeRepo") String typeRepo) {
        repository.removeAllWords(typeRepo);
        return htmlCreator.createHomePage();
    }

}
