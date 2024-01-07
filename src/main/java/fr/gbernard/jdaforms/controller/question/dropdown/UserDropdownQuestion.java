package fr.gbernard.jdaforms.controller.question.dropdown;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.*;
import lombok.*;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Asks the user to pick one member among a list of the guild's members
 */
@Getter @Setter
@Builder
@AllArgsConstructor
public class UserDropdownQuestion implements Question<List<User>> {

  private @NonNull String key;
  private String summaryTitle;
  private @NonNull final String title;
  private final String subtitle;

  @Builder.Default
  private int minSelectedItems = 1;
  @Builder.Default
  private int maxSelectedItems = 1;
  @Builder.Default
  private @NonNull Optional<List<User>> answer = Optional.empty();
  @Builder.Default
  private boolean complete = false;
  @Builder.Default
  private @NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion = form -> Optional.empty();

  public String getSummaryTitle() {
    return Optional.ofNullable(summaryTitle).orElse(title);
  }

  @Override
  public FormMessageHookEditor getMessageEditor() {
    return (InteractionHook hookToMessage, Form form) -> {

      final MessageEmbed embed = EmbedTemplate.basic(title, subtitle, EmbedColor.NEUTRAL);
      final EntitySelectMenu dropdownOptions =
          EntitySelectMenu
              .create(key, EntitySelectMenu.SelectTarget.USER)
              .setRequiredRange(minSelectedItems, maxSelectedItems)
              .build();
      EditMessage.embedAndItemComponents(hookToMessage, embed, List.of(dropdownOptions) );
    };
  }

  @Override
  public FormInteractionOptionalModal getModalProviderInsteadOfHandler() {
    return (discordReturnedValues, form) -> Optional.empty();
  }

  @Override
  public FormInteractionHandler getFormInteractionHandler() {
    return (discordReturnedValues, actions) -> actions.startNextQuestionWithoutAnswering();
  }

}
