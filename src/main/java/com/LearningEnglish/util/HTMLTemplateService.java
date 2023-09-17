package com.LearningEnglish.util;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HTMLTemplateService {

    private boolean first = true;
    private String css =
            "<html lang=\"en\">\n" +
                    "<head>" +
                    "<style>html {\n" +
                    "    background-color: #15202B;\n" +
                    "    font-family: 'Roboto', sans-serif;\n" +
                    "}\n" +
                    "\n" +
                    "    h1 {\n" +
                    "      color: #04AA6D;\n" +
                    "    }\n" +
                    "\n" +
                    ".navbar {\n" +
                    " background-color: #273B4F;\n" +
                    "margin: auto;\n" +
                    "  width: 60%;\n" +
                    "  padding: 10px;\n" +
                    "  padding-bottom: 50px;\n" +
                    "}\n" +
                    "\n" +
                    "a:link {text-decoration: none;}\n" +
                    "a {color: white;}\n" +
                    "\n" +
                    "ul {\n" +
                    "  list-style-type: none;\n" +
                    "  margin: 0;\n" +
                    "  padding: 0;\n" +
                    "}\n" +
                    "\n" +
                    "li:hover {background-color: #038555;}\n" +
                    "li {\n" +
                    "  display: inline;\n" +
                    "        padding: 8px;\n" +
                    "        font-family: roboto, sans-serif;\n" +
                    "        font-size: 18px;\n" +
                    "        border-radius: 10px;\n" +
                    "        margin: 2px;\n" +
                    "}\n" +
                    "\n" +
                    ".navbarButtons {\n" +
                    "      background-color: #04AA6D;\n" +
                    "      color: #FFFFFF;\n" +
                    "}\n" +
                    "\n" +
                    ".show:hover {background-color: #D45A2E;}\n" +
                    ".show {\n" +
                    "  display: inline;\n" +
                    "        padding: 8px;\n" +
                    "        background-color: #FF6C37;\n" +
                    "        font-family: roboto, sans-serif;\n" +
                    "        font-size: 20px;\n" +
                    "        border-radius: 10px;\n" +
                    "        margin: 5px;\n" +
                    "}\n" +
                    "\n" +
                    ".body {\n" +
                    "margin: auto;\n" +
                    "  width: 60%;\n" +
                    "  padding: 10px;\n" +
                    "  padding-top: 50px;\n" +
                    "  padding-bottom: 50px;\n" +
                    "}\n" +
                    "\n" +
                    ".text {\n" +
                    "    color: #04AA6D;\n" +
                    "    font-size: 18px;\n" +
                    "}\n" +
                    "\n" +
                    ".the_word {\n" +
                    "    color: #ADD8E6;\n" +
                    "    font-size: 20px;\n" +
                    "}\n" +
                    "\n" +
                    ".message {\n" +
                    "    color: #ADD8E6;\n" +
                    "    font-size: 20px;\n" +
                    "}</style>\n";
    private StringBuffer BASED_TEMPLATE = new StringBuffer(css +
            "<title>Learning Words</title>\n" +

            "<link rel=\"icon\" href=\"https://drive.google.com/uc?id=1vGXwxZQ8vKxD1zPSrK5Ko9CD2-vLmdlk\" type=\"image/x-icon\">" +

            "</head>\n" +
            "<body>\n" +
            "<div class=\"navbar\">\n" +
            "    <h1>Learning Words</h1>\n" +
            "    <ul>\n" +
            "        <li  class=\"navbarButtons\"><a href=\"http://localhost:8000/\">&#8962;</a></li>\n" +
            "        <li  class=\"navbarButtons\"><a href=\"http://localhost:8000/search\">&#x1F50E;&#xFE0E;</a></li>\n" +
            "        <li  class=\"navbarButtons\"><a href=\"http://localhost:8000/add\">Add Words</a></li>\n" +
            "        <li  class=\"navbarButtons\"><a href=\"http://localhost:8000/getE\">To English</a></li>\n" +
            "        <li  class=\"navbarButtons\"><a href=\"http://localhost:8000/getB\">To Bulgarian</a></li>\n" +
            "        <li  class=\"navbarButtons\"><a href=\"http://localhost:8000/getAddedWords\">Added Words</a></li>\n" +
            "        <li  class=\"navbarButtons\"><a href=\"http://localhost:8000/numberOfWords\">Last Words</a></li>\n" +
            "        <li  class=\"navbarButtons\"><a href=\"http://localhost:8000/showInternalRepo\">Show Added Words</a></li>\n" +
            "    </ul>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>");

    public String getCss() {
        return css;
    }

    public String getBASED_TEMPLATE(Integer numberOfWords) {

        if (first) {
            first = false;
            int index = BASED_TEMPLATE.indexOf("</h1>");
            BASED_TEMPLATE.insert(index, " " + numberOfWords).toString();
        }
        return BASED_TEMPLATE.toString();
    }

    public String insertInDevBodyBasedTemplate(String context) {
        int insertIndex = BASED_TEMPLATE.indexOf("</body>");
        String line = String.format("<div class=\"body\"><td><p class=\"text\">%s</p></td></div>", context);
        StringBuilder result = new StringBuilder(BASED_TEMPLATE);
        return result.insert(insertIndex, line).toString();
    }

    public String insertTextTD(String text) {
        return String.format("<td><p class=\"text\">%s</p></td>", text);
    }

    public String createSingleLineTextAndWord(String text, String word) {
        return String.format("<table>\n" +
                "<tr><td><p class=\"text\">%s </p></td>\n" +
                "<td><p class=\"the_word\">%s</p></td></tr></table><p></p>", text, word);
    }

    public String createSpanText(String text) {
        return String.format("<span class=\"text\">%s </span>", text);
    }
    public String createSpanWordText(String wordText) {
        return String.format("<span class=\"the_word\">%s </span>", wordText);
    }

    public String createLineForShow(String word, String word2) {
        return String.format("<table><tr><td><p class=\"text\">Word: </p></td>\n" +
                "            <td><p class=\"the_word\">%s</p></td>\n" +
                "            <td><p class=\"text\"> - </p></td>\n" +
                "            <td><p class=\"the_word\">%s</p></td></tr></table>", word, word2);
    }

    public String createInputField(String action, String object, String field, String text) {
        return String.format("<form action=\"%s\" method=\"post\">\n" +
                "            <table>\n" +
                "                <tr>\n" +
                "                    <td class=\"text\"><label for=\"%s.%s\">%s </label></td>\n" +
                "                    <td><input type=\"text\" name=\"%s\"></input></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td></td>\n" +
                "                    <td><input type=\"submit\" value=\"Submit\"></input></td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </form>", action, object, field, text, field);
    }

    public String create2InputFields(String action, String object, String field1, String text1, String field2, String text2) {
        return String.format("<form action=\"%s\" method=\"post\">\n" +
                "       <table>\n" +
                "            <tr>\n" +
                "                <td class=\"text\"><label for=\"%s.%s\">%s </label></td>\n" +
                "                <td><input type=\"text\" name=\"%s\"></input></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td class=\"text\"><label for=\"%s.%s\">%s </label></td>\n" +
                "                <td><input type=\"text\" name=\"%s\"></input></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td></td>\n" +
                "                <td><input type=\"submit\" value=\"Submit\"></input></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "   </form>", action, object, field1, text1, field1, object, field2, text2, field2);
    }

    public String createSeveralOrangeButton(String text, String url) {
        return String.format("<li class=\"show\"><a href=\"%s\">%s</a></li>", url, text);
    }

    public String createSingleOrangeButton(String text, String url) {
        return String.format("<li class=\"show\"><a href=\"%s\">%s</a></li><p></p>", url, text);
    }

    public String createLineWithButtons(List<String> buttons) {
        StringBuilder context = new StringBuilder("<ul>");
        for (int i = 0; i < buttons.size(); i++) {
            context.append(buttons.get(i));
        }
        context.append("</ul><p></p>");
        return context.toString();
    }
}
