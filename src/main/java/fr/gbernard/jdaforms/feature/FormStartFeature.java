package fr.gbernard.jdaforms.feature;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import fr.gbernard.jdaforms.repository.OngoingFormsRepository;
import fr.gbernard.jdaforms.utils.ExceptionUtils;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class FormStartFeature {

  public final static String LOADING_MESSAGE_TEXT = "**LOADING...**";

  private final OngoingFormsRepository ongoingFormsRepository = new OngoingFormsRepository();

  public void startForm(SlashCommandInteractionEvent command, Form discordForm) {
    Question<?> question = discordForm.getMandatoryQuestions().get(0);
    discordForm.setMessageId(command.getIdLong());
    discordForm.getQuestionsHistory().push(question);
    discordForm.setUserId(command.getUser().getIdLong());
    ongoingFormsRepository.save(discordForm);

    command
        .reply(LOADING_MESSAGE_TEXT)
        .setEphemeral(discordForm.isEphemeral())
        .queue(sentMessage -> {
          ExceptionUtils.uncheck(() -> question.getMessageEditor().edit(sentMessage, discordForm) );
        });
  }

}
