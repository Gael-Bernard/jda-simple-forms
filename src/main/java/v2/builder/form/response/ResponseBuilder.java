package v2.builder.form.response;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import v2.builder.form.question.QuestionBuilder;
import v2.builder.form.saver.AnswerSaveBuilder;
import v2.question.Question;

public interface ResponseBuilder {

  <
      T,
      E extends GenericInteractionCreateEvent,
      S extends AnswerSaveBuilder<T>,
      Q extends Question<T,E>,
      QB extends QuestionBuilder<T,E,S,Q>
      >
  S nextQuestion(QB question);

}
