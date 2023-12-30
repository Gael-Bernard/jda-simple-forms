package fr.gbernard.jdaforms.controller;

import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.repository.OngoingFormsRepository;
import fr.gbernard.jdaforms.service.FormContinueService;
import fr.gbernard.jdaforms.service.PermissionService;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
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

    if(!PermissionService.userAllowedAnswer(event.getMember().getIdLong(), form)) {
      event.reply(MessageCreateData.fromEmbeds( EmbedTemplate.basic("\uD83D\uDEAB Permission denied", "", EmbedColor.ERROR) ))
          .setEphemeral(true)
          .queue();
      return;
    }

    event.deferEdit().queue();
    formContinueService.continueForm(form, answer, hook);
  }

  /*

  @Override
  public void onStringSelectInteraction(StringSelectInteractionEvent event) {
    InteractionHook hook = event.getHook();
    long interactionId = event.getMessage().getInteraction().getIdLong();
    event.deferEdit().queue();
    evaluateStringsAndNext(interactionId, hook, event.getValues());
  }

  @Override
  public void onEntitySelectInteraction(EntitySelectInteractionEvent event) {
    InteractionHook hook = event.getHook();
    long interactionId = event.getMessage().getInteraction().getIdLong();

    List<User> users = event.getMentions().getUsers();
    if(!users.isEmpty()) {
      event.deferEdit().queue();
      evaluateUsersAndNext(interactionId, hook, users);
      return;
    }
  }

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
