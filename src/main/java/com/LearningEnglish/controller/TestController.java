package com.LearningEnglish.controller;

import com.LearningEnglish.service.HTMLTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
public class TestController {

    private TemplateEngine templateEngine;
    private final HTMLTemplateService htmlTemplateService;

    public TestController(TemplateEngine templateEngine, HTMLTemplateService htmlTemplateService) {
        this.templateEngine = templateEngine;
        this.htmlTemplateService = htmlTemplateService;
    }

    @GetMapping("/test")
    public String test() {
        String button = htmlTemplateService.createSeveralOrangeButton("test", "http://localhost:8000/test");
        String inputField = htmlTemplateService.createInputField("numberOfWordsE", "RequestDto", "request", "Number Of Last Added Words: ");
        String lineTextAndWord = htmlTemplateService.createSingleLineTextAndWord("Bulgarian:", "word");
        String s = htmlTemplateService.insertTextTD(button + lineTextAndWord + button + inputField);
        return s;
    }

    @GetMapping("/numberOfWords")
    public String numberOfWords() {
        String inputField = htmlTemplateService.createInputField("numberOfWords", "RequestDto", "request", "Number Of Last Added Words: ");
        return htmlTemplateService.insertInDevBodyBasedTemplate(inputField);
    }



    private String constructHTMLTemplate() {

        StringBuilder stringBuilder = new StringBuilder(getHTMLTemplate());
        String template = getHTMLTemplate();
        int i = template.indexOf("</p>");


        stringBuilder.insert(i + 4, String.format("<p><strong><span th:text=\"${1}\"></span></strong></p>"));


        Context context = new Context();
        context.setVariable("test", "test 1");
//        context.setVariable("1", "test 2");
        return templateEngine.process("test", context);
    }

    private String getHTMLTemplate () {
        Context context = new Context();
        return templateEngine.process("test", context);
    }

}
