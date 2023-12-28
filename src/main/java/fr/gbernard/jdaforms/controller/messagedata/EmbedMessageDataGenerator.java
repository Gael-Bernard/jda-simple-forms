package fr.gbernard.jdaforms.controller.messagedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

@Getter @Setter
@AllArgsConstructor
public class EmbedMessageDataGenerator {

  private MessageEmbed embed;

  public static MessageCreateData basicCreate(String title, String subtitle, EmbedColor color) {
    return MessageCreateData.fromEmbeds( basic(title, subtitle, color) );
  }

  public static MessageEditData basicEdit(String title, String subtitle, EmbedColor color) {
    return MessageEditData.fromEmbeds( basic(title, subtitle, color) );
  }

  private static MessageEmbed basic(String title, String subtitle, EmbedColor color) {
    return new EmbedBuilder()
        .setTitle(title)
        .setDescription(subtitle)
        .setColor(color.getCode())
        .build();
  }

}
