package v2.builder.form.saver;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import v2.builder.form.response.ResponseBuilder;

import java.util.List;

public class UserSelectSaveBuilder implements AnswerSaveBuilder<List<User>> {

  public ResponseBuilder<EntitySelectInteractionEvent> saveUsersOnKey(String key) {
    throw new UnsupportedOperationException();
  }

}
