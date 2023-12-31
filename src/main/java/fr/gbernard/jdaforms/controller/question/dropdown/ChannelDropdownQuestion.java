package fr.gbernard.jdaforms.controller.question.dropdown;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import lombok.*;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Asks the user to pick one channel among a list of the guild's channels
 */
@Getter @Setter
@Builder
@AllArgsConstructor
public class ChannelDropdownQuestion implements Question<List<GuildChannel>> {

  private @NonNull String key;
  private @NonNull final String title;
  private final String subtitle;

  @Builder.Default
  private int minSelectedItems = 1;
  @Builder.Default
  private int maxSelectedItems = 1;
  @Builder.Default
  private @NonNull Optional<List<GuildChannel>> answer = Optional.empty();
  @Builder.Default
  private @NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion = form -> Optional.empty();

  @Override
  public void editQuestionMessage(InteractionHook hookToMessage, Form form) {
    final MessageEmbed embed = EmbedTemplate.basic(title, subtitle, EmbedColor.NEUTRAL);
    final EntitySelectMenu dropdownOptions = EntitySelectMenu
        .create(key, EntitySelectMenu.SelectTarget.CHANNEL)
        .setRequiredRange(minSelectedItems, maxSelectedItems)
        .build();
    EditMessage.embedAndItemComponents(hookToMessage, embed, List.of(dropdownOptions) );
  }

  @Override
  public List<GuildChannel> parseAnswer(List<String> discordReturnedValues) {
    throw new UnsupportedOperationException("JDA entities (users, channels, etc.) are not supposed to be parsed.");
  }

}
