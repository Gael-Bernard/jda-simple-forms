package fr.gbernard.jdaforms.controller.action;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

import java.util.List;

public class EditMessage {

  public static void text(InteractionHook hook, String text) {
    hook
        .editOriginal(text)
        .setSuppressEmbeds(true)
        .setAttachments(List.of())
        .setComponents(List.of())
        .queue();
  }

  public static void embedAndItemComponents(InteractionHook hook, MessageEmbed embed, List<ItemComponent> itemComponents) {
    hook
        .editOriginal(MessageEditData.fromEmbeds(embed))
        .setContent("")
        .setComponents(List.of())
        .setActionRow(itemComponents)
        .setAttachments(List.of())
        .queue();
  }

}
