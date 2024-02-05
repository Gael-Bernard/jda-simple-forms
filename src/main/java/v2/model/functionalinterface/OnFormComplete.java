package v2.model.functionalinterface;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import v2.model.Form;

@FunctionalInterface
public interface OnFormComplete<E extends GenericInteractionCreateEvent> {

  void handle(E event, Form form);

}
