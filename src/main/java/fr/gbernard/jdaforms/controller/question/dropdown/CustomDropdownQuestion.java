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

    private @NonNull QuestionSharedFields<List<T>> sharedFields = new QuestionSharedFields<>();

    /**
     * Text to be displayed as description under the title
     */
    private @Nullable String subtitle;

    /**
     * Minimum number of items user should pick from the list
     */
    private int minSelectedItems;

    /**
     * Maximum number of items user should pick from the list
     */
    private int maxSelectedItems;

    /**
     * Items of type T to propose in the dropdown
     */
    private @NonNull List<T> choices;

    /**
     * Parser from the value of an item to an instance of the item
     * <br>
     * <br>For example with Vegetable{label:"DELICIOUS POTATO", value: "potato"},
     * <br>the parser would convert as follows:
     * <br>> "potato" -> Vegetable{label:"DELICIOUS POTATO", value: "potato"}
     */
    private @NonNull Function<String,T> parser;

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
