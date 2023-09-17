package com.LearningEnglish.controller;

import com.LearningEnglish.dto.AddWordsDto;
import com.LearningEnglish.dto.ResponseDto;
import com.LearningEnglish.model.Word;
import com.LearningEnglish.repository.Repository;
import com.LearningEnglish.service.WordService;
import com.LearningEnglish.service.HTMLService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    private final WordService wordService;
    private final Repository repository;
    private final HTMLService htmlCreator;

    public DemoController(WordService wordService, Repository repository, HTMLService htmlCreator) {
        this.wordService = wordService;
        this.repository = repository;
        this.htmlCreator = htmlCreator;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index() {
        return htmlCreator.createHomePage();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String add() {
        return htmlCreator.createAddPage();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute AddWordsDto addWordsDto) {
        wordService.addWords(addWordsDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("save");
        modelAndView.addObject("addWordsDto", addWordsDto);
        return modelAndView;
    }

    @RequestMapping(value = "/getE", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getE() {
        wordService.setToEnglish(true);
        return get();
    }

    @RequestMapping(value = "/getB", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getB() {
        wordService.setToEnglish(false);
        return get();
    }

    public String get() {
        ResponseDto response = wordService.getRandomWord();
        if (response.isSuccessful()) {
            Word word = response.getWord();
            return htmlCreator.createGetPage(wordService.isToEnglish(), word);
        } else {
            return htmlCreator.createWrongAnswerPage(wordService.isToEnglish(), response);
        }
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String show() {
        return htmlCreator.createShowPage(wordService.isToEnglish(), wordService.getCurrentWord());
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getAnswer(@ModelAttribute Word word) {
        ResponseDto responseDto = wordService.handleAnswer(word);
        if (responseDto.isSuccessful()) {
            ResponseDto randomWord = wordService.getRandomWord();
            if (randomWord.isSuccessful())  {
                return htmlCreator.createGetPage(wordService.isToEnglish(), randomWord.getWord());
            } else {
                return htmlCreator.createWrongAnswerPage(wordService.isToEnglish(), randomWord);
            }
        } else {
            return htmlCreator.createWrongAnswerPage(wordService.isToEnglish(), responseDto);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String search() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("show");
        return htmlCreator.searchPage();
    }

    @RequestMapping(value = "/searchResponse", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String searchResponse(@ModelAttribute Word request) {
        return htmlCreator.createSearchResponsePage(wordService.search(request));
    }


}