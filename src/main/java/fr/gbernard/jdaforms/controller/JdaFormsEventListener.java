package fr.gbernard.jdaforms.controller;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JdaFormsEventListener extends ListenerAdapter {

  /*

  @Override
  public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
    InteractionHook hook = event.getHook();
    long interactionId = event.getMessage().getInteraction().getIdLong();
    event.deferEdit().queue();
    evaluateStringsAndNext(interactionId, hook, List.of( event.getButton().getId() ) );
  }

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
