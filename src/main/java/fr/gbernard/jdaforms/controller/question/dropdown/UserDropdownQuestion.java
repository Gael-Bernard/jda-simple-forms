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
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Asks the user to pick one member among a list of the guild's members
 */
@Accessors(chain = true)
@Getter @Setter
public class UserDropdownQuestion implements Question<List<User>> {

  public static int DEFAULT_MIN_SELECTED = 1;
  public static int DEFAULT_MAX_SELECTED = 1;

  private @NonNull QuestionSharedFields<List<User>> sharedFields = new QuestionSharedFields<>();

  private @Nullable String subtitle;
  private int minSelectedItems = DEFAULT_MIN_SELECTED;
  private int maxSelectedItems = DEFAULT_MAX_SELECTED;

  @Override
  public @NotNull FormMessageHookEditor getMessageEditor() {
    return (InteractionHook hookToMessage, Form form) -> {

      final MessageEmbed embed = EmbedTemplate.basic(getTitle(), subtitle, EmbedColor.NEUTRAL);
      final EntitySelectMenu dropdownOptions =
          EntitySelectMenu
              .create(getKey(), EntitySelectMenu.SelectTarget.USER)
              .setRequiredRange(minSelectedItems, maxSelectedItems)
              .build();
      EditMessage.embedAndItemComponents(hookToMessage, embed, List.of(dropdownOptions) );
    };
  }

  @Override
  public @NotNull FormInteractionHandler getFormInteractionHandler() {
    return (discordReturnedValues, actions) -> actions.startNextQuestionWithoutAnswering();
  }

}
