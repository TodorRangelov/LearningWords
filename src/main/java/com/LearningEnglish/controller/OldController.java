//package com.LearningEnglish.controller;
//
//import com.LearningEnglish.dto.AddWordsDto;
//import com.LearningEnglish.dto.RequestDto;
//import com.LearningEnglish.dto.ResponseDto;
//import com.LearningEnglish.model.Word;
//import com.LearningEnglish.service.WordService;
//import com.LearningEnglish.util.HTMLCreator;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//public class OldController {
//
//    private final WordService wordService;
//    private final HTMLCreator htmlCreator;
//    private boolean toEnglish;
//
//    public OldController(WordService wordService, HTMLCreator htmlCreator) {
//        this.wordService = wordService;
//        this.htmlCreator = htmlCreator;
//    }
//
//
//    @RequestMapping(value = "/oldsave", method = RequestMethod.POST)
//    public ModelAndView save(@ModelAttribute AddWordsDto addWordsDto) {
//        wordService.addWords(addWordsDto);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("save");
//        modelAndView.addObject("addWordsDto", addWordsDto);
//        return modelAndView;
//    }
//
//
//    @RequestMapping(value = "/showB", method = RequestMethod.GET)
//    public ModelAndView showB() {
//        Word word = wordService.getCurrentWord();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("showB");
//        modelAndView.addObject("word", word);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/oldsearch", method = RequestMethod.GET)
//    public String search() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("show");
//        return "search";
//    }
//
//    @RequestMapping(value = "/oldsearchResponse", method = RequestMethod.POST)
//    public ModelAndView searchAnswer(@ModelAttribute Word request) {
//        Word word = wordService.search(request);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("searchResponse");
//        modelAndView.addObject("word", word);
//        return modelAndView;
//    }
//
//
//}