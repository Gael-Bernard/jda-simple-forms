package v2.question;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import v2.question.accepter.StringSelectAccepter;

import java.util.List;

public class UserSelectQuestion implements StringSelectAccepter<List<User>, EntitySelectInteractionEvent> {

  @Override
  public void sendFromStringSelectEvent(StringSelectInteractionEvent event) {

  }

}
