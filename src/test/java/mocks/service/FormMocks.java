package mocks.service;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;

import java.util.List;

public class FormMocks {

  public static Form initialised2YesNoQuestions() {
    final Question<Boolean> question1 = QuestionMocks.baseYesNoQuestion1();
    final Question<Boolean> question2 = QuestionMocks.baseYesNoQuestion2();

    return Form.builder()
        .messageId(663161270996041735L)
        .userId(150203841827045376L)
        .currentQuestion(question1)
        .questions(List.of(question1, question2))
        .build();
  }

}
