package v2.builder.form.question;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import v2.builder.form.saver.UserSelectSaveBuilder;
import v2.question.UserSelectQuestion;

import java.util.List;
import java.util.NoSuchElementException;

public class UserSelectQuestionBuilder implements QuestionBuilder<List<User>, EntitySelectInteractionEvent, UserSelectSaveBuilder, UserSelectQuestion> {

  @Override
  public List<User> getAnswer() throws NoSuchElementException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setStringKey(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public UserSelectQuestion build() {
    throw new UnsupportedOperationException();
  }
}
