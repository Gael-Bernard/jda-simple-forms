package fr.gbernard.jdaforms.controller.question.dropdown;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import lombok.*;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Asks the user to pick one item among a list of strings
 * @param <T> expected type
 */
@Builder
@Getter @Setter
@RequiredArgsConstructor
public class CustomDropdownQuestion<T extends DropdownItem> implements Question<T> {

    private @NonNull String key;
    private @NonNull final String title;
    private final String subtitle;

    @Builder.Default
    private @NonNull Optional<T> answer = Optional.empty();
    @Builder.Default
    private @NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion = form -> Optional.empty();

    @NonNull
    private final List<T> choices;
    @NonNull
    private final Function<String,T> parser;

    @Override
    public void editQuestionMessage(InteractionHook hookToMessage, Form form) {
        final MessageEmbed embed = EmbedTemplate.basic(title, subtitle, EmbedColor.NEUTRAL);
        final StringSelectMenu dropdownOptions = generateSelectMenu();
        EditMessage.embedAndItemComponents(hookToMessage, embed, List.of(dropdownOptions) );
    }

    protected StringSelectMenu generateSelectMenu() {
        StringSelectMenu.Builder builder = StringSelectMenu.create(getKey());
        for(T choi : choices) {
            builder.addOption(choi.getLabel(), choi.getValue(), choi.getEmoji());
        }
        return builder.build();
    }

    @Override
    public T parseAnswer(List<String> discordReturnedValues) throws IllegalArgumentException {
        return parser.apply( discordReturnedValues.get(0) );
    }
}
