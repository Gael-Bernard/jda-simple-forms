package fr.gbernard.jdaforms.controller.action;

import net.dv8tion.jda.api.interactions.InteractionHook;

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

}
