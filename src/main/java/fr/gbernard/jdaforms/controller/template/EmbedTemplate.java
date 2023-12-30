package fr.gbernard.jdaforms.controller.template;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * Builds the templates for the JDA Simple Forms library
 */
public class EmbedTemplate {

  /**
   * Simple embed with a title and description
   */
  public static MessageEmbed basic(String title, String subtitle, EmbedColor color) {
    return new EmbedBuilder()
        .setTitle(title)
        .setDescription(subtitle)
        .setColor(color.getCode())
        .build();
  }

}
