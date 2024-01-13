package fr.gbernard.jdaforms.controller.question.yesno;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Yes/No question
 */
@Accessors(chain = true)
@Getter @Setter
public class YesNoQuestion implements Question<Boolean> {

    public static String YES_BUTTON_ID = "yes";
    public static String NO_BUTTON_ID = "no";

    private @NonNull QuestionSharedFields<Boolean> sharedFields = new QuestionSharedFields<>();

    /**
     * Text to be displayed as description under the title
     */
    private @NonNull String subtitle;

    /**
     * Label displayed on the "YES" answer button
     */
    private @NonNull String yesLabel;

    /**
     * Label displayed on the "NO" answer button
     */
    private @NonNull String noLabel;

    @Override
    public @NotNull FormMessageHookEditor getMessageEditor() {
        return (InteractionHook hookToMessage, Form form) -> {

            MessageEmbed embed = EmbedTemplate.basic(getTitle(), getSubtitle(), EmbedColor.NEUTRAL);
            List<ItemComponent> actionRows = List.of(
                Button.primary(YES_BUTTON_ID, getYesLabel()),
                Button.danger(NO_BUTTON_ID, getNoLabel())
            );
            EditMessage.embedAndItemComponents(hookToMessage, embed, actionRows);
        };
    }

    @Override
    public @NotNull FormInteractionHandler getFormInteractionHandler() {
        return (discordReturnedValues, actions) -> {

            boolean parsedAnswer = discordReturnedValues.get(0).equals(YES_BUTTON_ID);
            actions.answerAndStartNextQuestion(parsedAnswer);
        };
    }

}
