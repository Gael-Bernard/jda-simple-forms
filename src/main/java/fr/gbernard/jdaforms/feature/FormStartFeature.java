package fr.gbernard.jdaforms.feature;

import fr.gbernard.jdaforms.controller.question.summary.ValidateSummaryQuestion;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import fr.gbernard.jdaforms.repository.OngoingFormsRepository;
import fr.gbernard.jdaforms.utils.ExceptionUtils;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FormStartFeature {

  public static String LOADING_MESSAGE_TEXT = "**LOADING...**";

  private final OngoingFormsRepository ongoingFormsRepository = new OngoingFormsRepository();
  private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(16);

  public void startForm(SlashCommandInteractionEvent command, Form discordForm) {
    Question<?> question = discordForm.getMandatoryQuestions().get(0);
    discordForm.setMessageId(command.getIdLong());
    discordForm.getQuestionsHistory().push(question);
    discordForm.setUserId(command.getUser().getIdLong());
    discordForm.mapMandatoryQuestionsToModifiable();
    discordForm.getMandatoryQuestions().add( new ValidateSummaryQuestion() );
    executorService.schedule(() -> {
      ongoingFormsRepository.delete(discordForm);
      command.getUser().openPrivateChannel().flatMap(
          channel -> channel.sendMessage(discordForm.getTimeoutMessageSupplier().apply(discordForm))).queue();
    }, 20, TimeUnit.MINUTES);
    ongoingFormsRepository.save(discordForm);

    command
        .reply(LOADING_MESSAGE_TEXT)
        .setEphemeral(discordForm.isEphemeral())
        .queue(sentMessage -> {
          ExceptionUtils.uncheck(() -> question.getMessageEditor().edit(sentMessage, discordForm) );
        });
  }

}
