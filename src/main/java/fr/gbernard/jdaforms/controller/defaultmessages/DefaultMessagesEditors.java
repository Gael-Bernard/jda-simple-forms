package fr.gbernard.jdaforms.controller.defaultmessages;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.FormMessageEditor;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class DefaultMessagesEditors {

  public static FormMessageEditor formSent() {
    return (hookToMessage, form) -> {

      MessageEmbed embed = EmbedTemplate
          .basic("✅ Done!", "Your answer(s) were successfully sent.", EmbedColor.SUCCESS);
      EditMessage.embed(hookToMessage, embed);
    };
  }

}
