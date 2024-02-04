package v2.builder.form;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import v2.builder.form.question.QuestionBuilder;
import v2.builder.form.saver.AnswerSaveBuilder;
import v2.question.Question;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormBuilder
    implements FormBuilderFirstQuestion {

  public static FormBuilder create() {
    return new FormBuilder();
  }


  @Override
  public <
      T,
      E extends GenericInteractionCreateEvent,
      S extends AnswerSaveBuilder<T>,
      Q extends Question<T,E>,
      QB extends QuestionBuilder<T,E,S,Q>
      >
  S firstQuestion(QB question) {
    throw new UnsupportedOperationException();
  }

}
