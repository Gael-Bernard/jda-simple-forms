package v2.question.accepter;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import v2.question.Question;

public interface StringSelectAccepter<T,E extends GenericInteractionCreateEvent> extends Question<T,E> {

  void sendFromStringSelectEvent(StringSelectInteractionEvent event);

}
