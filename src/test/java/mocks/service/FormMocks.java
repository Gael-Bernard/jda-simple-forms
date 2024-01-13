package mocks.service;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;

import java.util.List;
import java.util.Stack;

public class FormMocks {

  public static Form initialised2YesNoQuestions() {
    final Question<Boolean> question1 = QuestionMocks.baseYesNoQuestion1();
    final Question<Boolean> question2 = QuestionMocks.baseYesNoQuestion2();

    final Stack<Question<?>> history = new Stack<>();
    history.push(question1);

    return new Form()
        .setMessageId(663161270996041735L)
        .setUserId(150203841827045376L)
        .setMandatoryQuestions(List.of(question1, question2));
  }

}
