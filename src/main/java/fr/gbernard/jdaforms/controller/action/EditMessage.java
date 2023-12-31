package fr.gbernard.jdaforms.controller.action;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

import java.util.List;

/**
 * Util methods to override all the elements of a message
 */
public class EditMessage {

  /**
   * Edits message to base text
   * @param hook message to be edited
   * @param text text to be printed
   */
  public static void text(InteractionHook hook, String text) {
    hook
        .editOriginal(text)
        .setSuppressEmbeds(true)
        .setAttachments(List.of())
        .setComponents(List.of())
        .queue();
  }

  /**
   * Edits message to an embed with item components (buttons)
   * @param hook hook to the message to be edited
   * @param embed embed to insert
   * @param itemComponents item components to insert
   */
  public static void embedAndItemComponents(InteractionHook hook, MessageEmbed embed, List<ItemComponent> itemComponents) {
    hook
        .editOriginal(MessageEditData.fromEmbeds(embed))
        .setContent("")
        .setComponents(List.of())
        .setActionRow(itemComponents)
        .setAttachments(List.of())
        .queue();
  }

  /**
   *
   * @param hook
   * @param embed
   */
  public static void embed(InteractionHook hook, MessageEmbed embed) {
    hook
        .editOriginal(MessageEditData.fromEmbeds(embed))
        .setContent("")
        .setComponents(List.of())
        .setAttachments(List.of())
        .queue();
  }

}
