package fr.gbernard.jdaforms.controller;

import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import fr.gbernard.jdaforms.repository.OngoingFormsRepository;
import fr.gbernard.jdaforms.service.FormContinueService;
import fr.gbernard.jdaforms.service.PermissionService;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.List;
import java.util.Optional;

public class JdaFormsEventListener extends ListenerAdapter {

  private final FormContinueService formContinueService = new FormContinueService();
  private final OngoingFormsRepository ongoingFormsRepository = new OngoingFormsRepository();

  @Override
  public void onButtonInteraction(ButtonInteractionEvent event) {
    final long interactionId = event.getMessage().getInteraction().getIdLong();
    final List<String> answer = List.of( event.getButton().getId() );
    final InteractionHook hook = event.getHook();

    Form form;
    {
      Optional<Form> formOpt = ongoingFormsRepository.getById(interactionId);
      if(formOpt.isEmpty()) { return; }
      form = formOpt.get();
    }

    if(!PermissionService.userAllowedAnswer(event.getUser().getIdLong(), form)) {
      event.reply(MessageCreateData.fromEmbeds( EmbedTemplate.basic("\uD83D\uDEAB Permission denied", "", EmbedColor.ERROR) ))
          .setEphemeral(true)
          .queue();
      return;
    }

    event.deferEdit().queue();

    formContinueService.setAnswerInForm(form, answer);
    FormContinueService.updateFormToNextQuestion(form);
    formContinueService.sendCurrentQuestionOrEnd(form, hook);
    formContinueService.saveOrDeleteForm(form);
  }

  public void onStringSelectInteraction(StringSelectInteractionEvent event) {
    final long interactionId = event.getMessage().getInteraction().getIdLong();
    final List<String> answer = event.getValues();
    final InteractionHook hook = event.getHook();

    Form form;
    {
      Optional<Form> formOpt = ongoingFormsRepository.getById(interactionId);
      if(formOpt.isEmpty()) { return; }
      form = formOpt.get();
    }

    if(!PermissionService.userAllowedAnswer(event.getUser().getIdLong(), form)) {
      event.reply(MessageCreateData.fromEmbeds( EmbedTemplate.basic("\uD83D\uDEAB Permission denied", "", EmbedColor.ERROR) ))
          .setEphemeral(true)
          .queue();
      return;
    }

    event.deferEdit().queue();

    formContinueService.setAnswerInForm(form, answer);
    FormContinueService.updateFormToNextQuestion(form);
    formContinueService.sendCurrentQuestionOrEnd(form, hook);
    formContinueService.saveOrDeleteForm(form);
  }

  @Override
  public void onEntitySelectInteraction(EntitySelectInteractionEvent event) {
    final long interactionId = event.getMessage().getInteraction().getIdLong();
    final InteractionHook hook = event.getHook();

    Form form;
    {
      Optional<Form> formOpt = ongoingFormsRepository.getById(interactionId);
      if(formOpt.isEmpty()) { return; }
      form = formOpt.get();
    }

    if(!PermissionService.userAllowedAnswer(event.getUser().getIdLong(), form)) {
      event.reply(MessageCreateData.fromEmbeds( EmbedTemplate.basic("\uD83D\uDEAB Permission denied", "", EmbedColor.ERROR) ))
          .setEphemeral(true)
          .queue();
      return;
    }

    final List<User> users = event.getMentions().getUsers();
    final List<GuildChannel> channels = event.getMentions().getChannels();
    final List<Role> roles = event.getMentions().getRoles();
    final Question<?> currentQuestion = form.getCurrentQuestion().orElseThrow(IllegalStateException::new);

    if(!users.isEmpty()) {
      ((Question<List<User>>) currentQuestion).setAnswer( Optional.of(users) );
    }
    else if(!channels.isEmpty()) {
      ((Question<List<GuildChannel>>) currentQuestion).setAnswer( Optional.of(channels) );
    }
    else if(!roles.isEmpty()) {
      ((Question<List<Role>>) currentQuestion).setAnswer( Optional.of(roles) );
    }
    else {
      throw new IllegalArgumentException("Expected a user, channel or role, while none of these is available");
    }

    event.deferEdit().queue();

    FormContinueService.updateFormToNextQuestion(form);
    formContinueService.sendCurrentQuestionOrEnd(form, hook);
    formContinueService.saveOrDeleteForm(form);
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
