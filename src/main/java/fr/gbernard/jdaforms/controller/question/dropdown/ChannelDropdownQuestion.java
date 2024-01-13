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
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Asks the user to pick one channel among a list of the guild's channels
 */
@Accessors(chain = true)
@Getter @Setter
public class ChannelDropdownQuestion implements Question<List<GuildChannel>> {

  private @NonNull QuestionSharedFields<List<GuildChannel>> sharedFields = new QuestionSharedFields<>();

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

  @Override
  public @NotNull FormMessageHookEditor getMessageEditor() {
    return (InteractionHook hookToMessage, Form form) -> {

      final MessageEmbed embed = EmbedTemplate.basic(getTitle(), subtitle, EmbedColor.NEUTRAL);
      final EntitySelectMenu dropdownOptions = EntitySelectMenu
          .create(getKey(), EntitySelectMenu.SelectTarget.CHANNEL)
          .setRequiredRange(minSelectedItems, maxSelectedItems)
          .build();
      EditMessage.embedAndItemComponents(hookToMessage, embed, List.of(dropdownOptions) );
    };
  }

  @Override
  public @NotNull FormInteractionOptionalModal getModalProviderInsteadOfHandler() {
    return (discordReturnedValues, form) -> Optional.empty();
  }

  @NotNull
  @Override
  public FormInteractionHandler getFormInteractionHandler() {
    return (discordReturnedValues, actions) -> actions.startNextQuestionWithoutAnswering();
  }

}
