package v2.builder.form.question;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import v2.builder.form.saver.AnswerSaveBuilder;
import v2.question.Question;

import java.util.NoSuchElementException;

public interface QuestionBuilder<
    T,
    E extends GenericInteractionCreateEvent,
    S extends AnswerSaveBuilder<T>,
    Q extends Question<T,E>
    > {

  T getAnswer() throws NoSuchElementException;

  void setStringKey(String key);

  Q build();

}
