package v2.question;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import v2.question.accepter.StringSelectAccepter;

import java.util.List;

public class StringSelectQuestion implements StringSelectAccepter<List<String>, StringSelectInteractionEvent> {

  @Override
  public void sendFromStringSelectEvent(StringSelectInteractionEvent event) {
    throw new UnsupportedOperationException();
  }

}
