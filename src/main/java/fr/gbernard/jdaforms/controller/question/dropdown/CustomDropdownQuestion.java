package fr.gbernard.jdaforms.controller.question.dropdown;

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
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Asks the user to pick one item among a list of strings
 * @param <T> expected type
 */
@Accessors(chain = true)
@Getter @Setter
public class CustomDropdownQuestion<T extends DropdownItem> implements Question<List<T>> {

    public static int DEFAULT_MIN_SELECTED = 1;
    public static int DEFAULT_MAX_SELECTED = 1;
    public static Function<String,?> DEFAULT_PARSER = s -> s;

    private @NonNull QuestionSharedFields<List<T>> sharedFields = new QuestionSharedFields<>();

    private @Nullable String subtitle;
    private int minSelectedItems = DEFAULT_MIN_SELECTED;
    private int maxSelectedItems = DEFAULT_MAX_SELECTED;

    private @NonNull List<T> choices;
    private @NonNull Function<String,T> parser = (Function<String,T>) DEFAULT_PARSER;

    @Override
    public @NotNull FormMessageHookEditor getMessageEditor() {
        return (InteractionHook hookToMessage, Form form) -> {

            final MessageEmbed embed = EmbedTemplate.basic(getTitle(), subtitle, EmbedColor.NEUTRAL);
            final StringSelectMenu dropdownOptions = generateSelectMenu();
            EditMessage.embedAndItemComponents(hookToMessage, embed, List.of(dropdownOptions) );
        };
    }

    protected StringSelectMenu generateSelectMenu() {
        StringSelectMenu.Builder builder = StringSelectMenu.create(getKey());
        for(T choi : choices) {
            builder.addOption(choi.getLabel(), choi.getValue(), choi.getEmoji());
        }
        return builder
            .setRequiredRange(minSelectedItems, maxSelectedItems)
            .build();
    }

    @Override
    public @NotNull FormInteractionHandler getFormInteractionHandler() {
        return (discordReturnedValues, actions) -> {

            List<T> parsedAnswer = discordReturnedValues.stream()
                .map(parser)
                .collect(Collectors.toList());

            actions.answerAndStartNextQuestion(parsedAnswer);
        };
    }

}
