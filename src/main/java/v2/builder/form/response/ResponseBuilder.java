package v2.builder.form.response;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import v2.builder.form.question.QuestionBuilder;
import v2.builder.form.saver.AnswerSaveBuilder;
import v2.model.Form;
import v2.model.functionalinterface.OnFormComplete;
import v2.question.accepter.StringSelectAccepter;

public class ResponseBuilder<E extends GenericInteractionCreateEvent> {

  public <
      T2,
      E2 extends GenericInteractionCreateEvent,
      S2 extends AnswerSaveBuilder<T2>,
      Q2 extends StringSelectAccepter<T2,E2>,
      QB2 extends QuestionBuilder<T2,E2,S2,Q2>
      >
  S2 nextQuestion(QB2 question) {
    throw new UnsupportedOperationException();
  }

  public Form onFormComplete(OnFormComplete<E> handler) {
    throw new UnsupportedOperationException();
  }

}
