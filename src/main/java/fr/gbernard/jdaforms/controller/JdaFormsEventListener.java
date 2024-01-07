package fr.gbernard.jdaforms.controller;

import fr.gbernard.jdaforms.business.PermissionBusiness;
import fr.gbernard.jdaforms.business.QuestionCompletionBusiness;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.feature.FormContinueFeature;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.repository.OngoingFormsRepository;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.List;

public class JdaFormsEventListener extends ListenerAdapter {

  private final QuestionCompletionBusiness questionCompletionBusiness = new QuestionCompletionBusiness();
  private final FormContinueFeature formContinueFeature = new FormContinueFeature();
  private final OngoingFormsRepository ongoingFormsRepository = new OngoingFormsRepository();

  @Override
  public void onButtonInteraction(ButtonInteractionEvent event) {
    final long interactionId = event.getMessage().getInteraction().getIdLong();
    final List<String> answer = List.of( event.getButton().getId() );

    Form form = ongoingFormsRepository.getById(interactionId).orElse(null);
    if(form == null) {
      return;
    }

    if(!PermissionBusiness.userAllowedAnswer(event.getUser().getIdLong(), form)) {
      event.reply(MessageCreateData.fromEmbeds( EmbedTemplate.basic("\uD83D\uDEAB Permission denied", "", EmbedColor.ERROR) ))
          .setEphemeral(true)
          .queue();
      return;
    }

    formContinueFeature.triggerCurrentQuestionInteractionHandler(event, answer, form);
    formContinueFeature.saveOrDeleteForm(form);
  }

  public void onStringSelectInteraction(StringSelectInteractionEvent event) {
    final long interactionId = event.getMessage().getInteraction().getIdLong();
    final List<String> answer = event.getValues();

    Form form = ongoingFormsRepository.getById(interactionId).orElse(null);
    if(form == null) {
      return;
    }

    if(!PermissionBusiness.userAllowedAnswer(event.getUser().getIdLong(), form)) {
      event.reply(MessageCreateData.fromEmbeds( EmbedTemplate.basic("\uD83D\uDEAB Permission denied", "", EmbedColor.ERROR) ))
          .setEphemeral(true)
          .queue();
      return;
    }

    formContinueFeature.triggerCurrentQuestionInteractionHandler(event, answer, form);
    formContinueFeature.saveOrDeleteForm(form);
  }

  @Override
  public void onEntitySelectInteraction(EntitySelectInteractionEvent event) {
    final long interactionId = event.getMessage().getInteraction().getIdLong();

    Form form = ongoingFormsRepository.getById(interactionId).orElse(null);
    if(form == null) {
      return;
    }

    if(!PermissionBusiness.userAllowedAnswer(event.getUser().getIdLong(), form)) {
      event.reply(MessageCreateData.fromEmbeds( EmbedTemplate.basic("\uD83D\uDEAB Permission denied", "", EmbedColor.ERROR) ))
          .setEphemeral(true)
          .queue();
      return;
    }

    final List<User> users = event.getMentions().getUsers();
    final List<GuildChannel> channels = event.getMentions().getChannels();
    final List<Role> roles = event.getMentions().getRoles();

    if(!users.isEmpty()) {
      questionCompletionBusiness.setAnswerOfCurrentQuestion(form, users);
    }
    else if(!channels.isEmpty()) {
      questionCompletionBusiness.setAnswerOfCurrentQuestion(form, channels);
    }
    else if(!roles.isEmpty()) {
      questionCompletionBusiness.setAnswerOfCurrentQuestion(form, roles);
    }
    else {
      throw new IllegalArgumentException("Expected a user, channel or role, while none of these is available");
    }

    formContinueFeature.triggerCurrentQuestionInteractionHandler(event, List.of(), form);
    formContinueFeature.saveOrDeleteForm(form);
  }

/*

  @Override
  public void onModalInteraction(ModalInteractionEvent event) {
    InteractionHook hook = event.getHook();
    long interactionId = event.getMessage().getInteraction().getIdLong();
    List<String> values = event.getValues().stream().map(ModalMapping::getAsString).collect(Collectors.toList());

    event.deferEdit().queue();
    evaluateStringsAndNext(interactionId, hook, values);
  }

*/

}
