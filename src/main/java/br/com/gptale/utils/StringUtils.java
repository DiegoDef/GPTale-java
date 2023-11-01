package br.com.gptale.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static br.com.gptale.utils.ChatGPTPromptUtils.OPCAO_1;
import static br.com.gptale.utils.ChatGPTPromptUtils.OPTIONS;


@UtilityClass
public class StringUtils {

    public String extractParagraph(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        String[] paragraphs = text.split("\n\n");
        StringBuilder extractedParagraphs = new StringBuilder();

        for (String paragraph : paragraphs) {
            if (paragraph.contains(OPCAO_1)) {
                break;
            }
            extractedParagraphs.append(paragraph).append("\n\n");
        }

        if (Objects.equals(extractedParagraphs.toString(), "")) {
            int index = text.indexOf(OPCAO_1);
            extractedParagraphs.append(text, 0, index);
        }

        return extractedParagraphs.toString().trim().replaceAll("(^\\n+)|(\\n+$)", "");
    }

    public List<String> extractOptions(String text) {
        List<String> options = new ArrayList<>();
        String[] lines = text.split("\n");
        Pattern pattern = Pattern.compile("^Opção \\d+: ", Pattern.CANON_EQ);

        for (String line : lines) {
            if (containsOption(line)) {
                String option = pattern.matcher(line).replaceAll("").trim();
                options.add(option);
            }
        }

        return options;
    }

    private static boolean containsOption(String text) {
        for (String str : OPTIONS) {
            if (text.contains(str)) {
                return true;
            }
        }
        return false;
    }
}
