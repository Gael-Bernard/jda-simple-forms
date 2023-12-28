package fr.gbernard.jdaforms.controller.question.yesno;

import fr.gbernard.jdaforms.controller.messagedata.EmbedColor;
import fr.gbernard.jdaforms.controller.messagedata.EmbedMessageDataGenerator;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import lombok.*;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void editQuestionMessage(InteractionHook hookToMessage, Form form) {
        MessageEditData embed = EmbedMessageDataGenerator.basicEdit(getTitle(), getSubtitle(), EmbedColor.NEUTRAL);
        List<ItemComponent> actionRows = List.of(
            Button.primary(YES_BUTTON_ID, getYesLabel()),
            Button.danger(NO_BUTTON_ID, getNoLabel())
        );

        hookToMessage
            .editOriginal(embed)
            .setContent("")
            .setActionRow(actionRows)
            .queue();
    }

    @Override
    public Boolean parseAnswer(List<String> discordReturnedValues) {
        return discordReturnedValues.get(0).equals(YES_BUTTON_ID);
    }
}
