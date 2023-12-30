package fr.gbernard.jdaforms.controller.question.yesno;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import lombok.*;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Yes/No question
 */
@Builder
@Getter @Setter
@AllArgsConstructor
public class YesNoQuestion implements Question<Boolean> {

    public static String YES_BUTTON_ID = "yes";
    public static String NO_BUTTON_ID = "no";
    public static String DEFAULT_YES_LABEL = "YES";
    public static String DEFAULT_NO_LABEL = "NO";

    private @NonNull String key;
    private @NonNull String title;
    @Builder.Default
    private @NonNull String subtitle = "";
    @Builder.Default
    private String yesLabel = DEFAULT_YES_LABEL;
    @Builder.Default
    private String noLabel = DEFAULT_NO_LABEL;

    @Builder.Default
    private @NonNull Optional<Boolean> answer = Optional.empty();
    @Builder.Default
    private @NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion = form -> Optional.empty();

    @Override
    public void editQuestionMessage(InteractionHook hookToMessage, Form form) {
        MessageEmbed embed = EmbedTemplate.basic(getTitle(), getSubtitle(), EmbedColor.NEUTRAL);
        List<ItemComponent> actionRows = List.of(
            Button.primary(YES_BUTTON_ID, getYesLabel()),
            Button.danger(NO_BUTTON_ID, getNoLabel())
        );

        EditMessage.embedAndItemComponents(hookToMessage, embed, actionRows);
    }

    @Override
    public Boolean parseAnswer(List<String> discordReturnedValues) {
        return discordReturnedValues.get(0).equals(YES_BUTTON_ID);
    }
}