package fr.gbernard.jdaforms.controller.action;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.callbacks.IMessageEditCallback;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

import java.util.List;

/**
 * Util methods to override all the elements of a message
 */
public class EditMessage {

  /**
   * Edits message to base text
   * @param message message to be edited
   * @param text text to be printed
   */
  public static void text(IMessageEditCallback message, String text) {
    message
        .editMessage(text)
        .setSuppressEmbeds(true)
        .setAttachments(List.of())
        .setComponents(List.of())
        .queue();
  }

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
   * @param message message to be edited
   * @param embed embed to insert
   * @param itemComponents item components to insert
   */
  public static void embedAndItemComponents(IMessageEditCallback message, MessageEmbed embed, List<ItemComponent> itemComponents) {
    message
        .editMessage(MessageEditData.fromEmbeds(embed))
        .setContent("")
        .setComponents(List.of())
        .setActionRow(itemComponents)
        .setAttachments(List.of())
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
   * Edits message to an embed only
   * @param message message to be edited
   * @param embed embed to insert
   */
  public static void embed(IMessageEditCallback message, MessageEmbed embed) {
    message
        .editMessage(MessageEditData.fromEmbeds(embed))
        .setContent("")
        .setComponents(List.of())
        .setAttachments(List.of())
        .queue();
  }

  /**
   * Edits message to an embed only
   * @param hook hook to message to be edited
   * @param embed embed to insert
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
