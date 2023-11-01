package br.com.gptale.utils;

import br.com.gptale.dto.SendOptionDTO;
import br.com.gptale.entity.Story;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ChatGPTPromptUtils {

    public static final String OPCAO_1 = "Opção 1:";

    public static final String OPCAO_2 = "Opção 2:";

    public static final String OPCAO_3 = "Opção 3:";

    public static final String OPCAO_4 = "Opção 4:";

    public static final List<String> OPTIONS = List.of(OPCAO_1, OPCAO_2, OPCAO_3, OPCAO_4);

    public String createInitialMessage(Story story) {
        return String.format("Vamos jogar uma história interativa. O tema da história é \"%s\". O gênero textual é " +
                "%s. Comece a história e a cada parágrafo da história, me dê 4 opções para escolher e continuar a " +
                "história. Sempre inicie as opções com '%s' ou '%s' ou '%s' ou '%s'. A história deverá ter %s " +
                "parágrafos. O primeiro parágrafo enviado será o primeiro de %s parágrafos. No último parágrafo " +
                "explicite que a história acabou. E as continuações da história só devem ser enviadas quando " +
                "solicitado pelo usuário. Não envie a continuação da história na primeira mensagem, envia apenas o " +
                "parágrafo e as 4 opções para o usuário escolher.", story.getTitle(), story.getGender(), OPCAO_1,
                OPCAO_2, OPCAO_3, OPCAO_4, story.getParagraphCount(), story.getParagraphCount());
    }

    public String createOptionMessage(Story story, SendOptionDTO dto) {
        String optionMessage = String.format("Escolhi a opção %s%n", dto.getOption());
        if (story.getParagraphCount() >= story.getMaxParagraph()) {
            return optionMessage + "O próximo parágrafo será o último. Encerre a história, explicite" +
                    "que a historia " + "acabou e não dê mais nenhuma opção de continuação.";
        }
        return optionMessage + String.format("O próximo parágrafo é o %s de %s",
                story.getParagraphCount(), story.getMaxParagraph());
    }
}
