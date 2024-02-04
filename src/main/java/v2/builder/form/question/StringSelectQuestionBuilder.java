package v2.builder.form.question;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import v2.builder.form.saver.StringSelectSaveBuilder;
import v2.question.StringSelectQuestion;

import java.util.List;
import java.util.NoSuchElementException;

public class StringSelectQuestionBuilder implements QuestionBuilder<List<String>, StringSelectInteractionEvent, StringSelectSaveBuilder, StringSelectQuestion>  {

  @Override
  public List<String> getAnswer() throws NoSuchElementException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setStringKey(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public StringSelectQuestion build() {
    throw new UnsupportedOperationException();
  }

}
