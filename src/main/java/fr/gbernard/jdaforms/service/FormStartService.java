package fr.gbernard.jdaforms.service;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import fr.gbernard.jdaforms.utils.ExceptionUtils;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class FormStartService {

  public final static String LOADING_MESSAGE_TEXT = "**LOADING...**";

  public void startForm(SlashCommandInteractionEvent command, Form discordForm) {
    Question<?> question = discordForm.getQuestions().get(0);
    command
        .reply(LOADING_MESSAGE_TEXT)
        .setEphemeral(discordForm.isEphemeral())
        .queue(sentMessage -> {
          ExceptionUtils.uncheck(() -> question.editQuestionMessage(sentMessage, discordForm) );
        });
  }

}
