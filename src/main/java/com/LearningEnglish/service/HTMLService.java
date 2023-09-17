package com.LearningEnglish.service;

import com.LearningEnglish.dto.ResponseDto;
import com.LearningEnglish.model.Word;
import com.LearningEnglish.repository.WordRepository;
import com.LearningEnglish.util.HTMLTemplateService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HTMLService {

    private final HTMLTemplateService htmlTemplateService;
    private final WordRepository wordRepository;

    public HTMLService(HTMLTemplateService htmlTemplateService, WordRepository wordRepository) {
        this.htmlTemplateService = htmlTemplateService;
        this.wordRepository = wordRepository;
    }

    public String createHomePage() {
        String basedTemplate = htmlTemplateService.getBASED_TEMPLATE(wordRepository.getNumberOfWords());
        return basedTemplate;
    }

    private String buttonTemplateSeveral(String text, String prefix) {
        return htmlTemplateService.createSeveralOrangeButton(text, "http://localhost:8000/" + prefix);
    }

    private String buttonEditSeveral() {
        return htmlTemplateService.createSeveralOrangeButton("Edit", "http://localhost:8000/edit");
    }

    private String buttonContinueGetESeveral() {
        return htmlTemplateService.createSeveralOrangeButton("Continue", "http://localhost:8000/getE");
    }

    private String buttonContinueGetBSeveral() {
        return htmlTemplateService.createSeveralOrangeButton("Continue", "http://localhost:8000/getB");
    }

    private String buttonAddWordSeveral() {
        return htmlTemplateService.createSeveralOrangeButton("Add Word", "http://localhost:8000/addWordInInternalRepository");
    }

    private String buttonShow() {
        return htmlTemplateService.createSingleOrangeButton("Show", "http://localhost:8000/show");
    }

    private String createLineWithButtons(String... buttons) {
        return htmlTemplateService.createLineWithButtons(new ArrayList<>(List.of(buttons)));
    }

    public String createLinesOfWords(List<Word> words) {
        StringBuilder context = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            Word currentWord = words.get(i);
            String spanWordText = htmlTemplateService.createSpanWordText(String.format("%s - %s", currentWord.getToEnglish(), currentWord.getToBulgarian()));
            context.append(createLineWithButtons(spanWordText));
        }
        return context.toString();
    }

    public String createPageListOfWordsWithRemoveB(List<Word> words, String typeRepo) {
        StringBuilder context = new StringBuilder();

        context.append(createLineWithButtons(buttonTemplateSeveral("Remove All", String.format("removeAll/%s", typeRepo))));


        for (int i = 0; i < words.size(); i++) {
            Word currentWord = words.get(i);
            String removeButton = htmlTemplateService.createSeveralOrangeButton(
                    "Remove ", String.format("http://localhost:8000/removeFrom%sRepo/%s", typeRepo, currentWord.getId()));
            String spanWordText = htmlTemplateService.createSpanWordText(String.format("%s - %s", currentWord.getToEnglish(), currentWord.getToBulgarian()));
            context.append(createLineWithButtons(removeButton, spanWordText));
        }
        return htmlTemplateService.insertInDevBodyBasedTemplate(context.toString());
    }

    public String createInternalRepoPage() {
        String buttonTemplate = buttonTemplateSeveral("Temporary", "showTemporary");
        String buttonPersistent = buttonTemplateSeveral("Persistent", "showPersistent");
        return htmlTemplateService.insertInDevBodyBasedTemplate(createLineWithButtons(buttonPersistent, buttonTemplate));
    }

    public String createAddPage() {
        String context = htmlTemplateService.createInputField("save", "addWordsDto", "words", "Words: ");
        return htmlTemplateService.insertInDevBodyBasedTemplate(context);
    }

    public String createGetPage(boolean toEnglish, Word word) {
        if (toEnglish) {
            String lineTextAndWord = htmlTemplateService.createSingleLineTextAndWord("English", word.getToEnglish());
            String showButton = htmlTemplateService.createSingleOrangeButton("Show", "http://localhost:8000/show");
            String inputField = htmlTemplateService.createInputField("/answer", "word", "toBulgarian", "Bulagarian: ");
            return htmlTemplateService.insertInDevBodyBasedTemplate(showButton + lineTextAndWord + inputField);
        } else {
            String lineTextAndWord = htmlTemplateService.createSingleLineTextAndWord("Bulgarian", word.getToBulgarian());
            String showButton = htmlTemplateService.createSingleOrangeButton("Show", "http://localhost:8000/show");
            String inputField = htmlTemplateService.createInputField("/answer", "word", "toEnglish", "English: ");
            return htmlTemplateService.insertInDevBodyBasedTemplate(showButton + lineTextAndWord + inputField);
        }
    }

    public String createShowPage(boolean toEnglish, Word word) {
        String buttonContinue;
        String line;
        String buttonAddWord = buttonAddWordSeveral();
        if (toEnglish) {
            buttonContinue = buttonContinueGetESeveral();
            line = htmlTemplateService.createLineForShow(word.getToEnglish(), word.getToBulgarian());
        } else {
            buttonContinue = buttonContinueGetBSeveral();
            line = htmlTemplateService.createLineForShow(word.getToBulgarian(), word.getToEnglish());
        }
        String buttonEdit = buttonEditSeveral();
        String lineWithButtons = createLineWithButtons(buttonContinue, buttonAddWord, buttonEdit);
        return htmlTemplateService.insertInDevBodyBasedTemplate(lineWithButtons + line);
    }

    public String createWrongAnswerPage(boolean toEnglish, ResponseDto response) {
        String buttonContinue;
        if (toEnglish) {
            buttonContinue = buttonContinueGetESeveral();
        } else {
            buttonContinue = buttonContinueGetBSeveral();
        }

        String lineTextAndWord = htmlTemplateService.createSingleLineTextAndWord(" ", response.getMessage());
        if (response.getMessage().contains("Finished")) {
            return htmlTemplateService.insertInDevBodyBasedTemplate(lineTextAndWord);
        }
        String buttonAddWord = buttonAddWordSeveral();
        String buttonEdit = buttonEditSeveral();
        String lineWithButtons = createLineWithButtons(buttonContinue, buttonAddWord, buttonEdit);
        return htmlTemplateService.insertInDevBodyBasedTemplate(lineWithButtons + lineTextAndWord);
    }

    public String createEditPage(Word currentWord) {
        String lineCurrentWord = htmlTemplateService.createLineForShow(currentWord.getToEnglish(), currentWord.getToBulgarian());
        String inputField = htmlTemplateService.create2InputFields(
                "edit", "word", "toEnglish", "Enter English Word : ", "toBulgarian", "Enter Bulgarian Word : ");
        return htmlTemplateService.insertInDevBodyBasedTemplate(lineCurrentWord + inputField);
    }

    public String createEditSuccessPage(Word currentWord) {
        String lineForShow = htmlTemplateService.createSingleLineTextAndWord(
                "Successful Edit Word! ", String.format("%s - %s", currentWord.getToEnglish(), currentWord.getToBulgarian()));
        return htmlTemplateService.insertInDevBodyBasedTemplate(lineForShow);
    }

    public String searchPage() {
        String inputFields = htmlTemplateService.create2InputFields("searchResponse", "word", "toBulgarian", "Bulgarian: ", "toEnglish", "English: ");
        return htmlTemplateService.insertInDevBodyBasedTemplate(inputFields);
    }

    public String createSearchResponsePage(ResponseDto responseDto) {
        if (responseDto.isSuccessful()) {
            String buttonAddWord = buttonAddWordSeveral();
            String buttonEdit = buttonEditSeveral();
            String lineWithButtons = createLineWithButtons(buttonAddWord, buttonEdit);

            return htmlTemplateService.insertInDevBodyBasedTemplate(lineWithButtons + createLinesOfWords(responseDto.getWords()));
        } else {
            String lineWord = htmlTemplateService.createSingleLineTextAndWord(
                    "Word: ", String.format("%s - %s", responseDto.getWord().getToEnglish(), responseDto.getWord().getToBulgarian()));
            return htmlTemplateService.insertInDevBodyBasedTemplate(lineWord);
        }
    }
}
