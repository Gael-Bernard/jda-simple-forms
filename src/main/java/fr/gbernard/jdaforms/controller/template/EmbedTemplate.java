package fr.gbernard.jdaforms.controller.template;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class EmbedTemplate {

  public static MessageEmbed basic(String title, String subtitle, EmbedColor color) {
    return new EmbedBuilder()
        .setTitle(title)
        .setDescription(subtitle)
        .setColor(color.getCode())
        .build();
  }

}
