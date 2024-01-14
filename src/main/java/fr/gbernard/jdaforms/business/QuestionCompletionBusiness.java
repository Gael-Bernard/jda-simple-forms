package fr.gbernard.jdaforms.business;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;

public class QuestionCompletionBusiness {

  public <T> void setAnswerOfCurrentQuestion(Form form, T answer) {
    final Question<T> currentQuestion = (Question<T>) form.getCurrentQuestion();
    currentQuestion.getSharedFields().setAnswer(answer);
  }

  public <T> void completeWithAnswer(Question<T> currentQuestion, T answer) {
    currentQuestion.getSharedFields().setAnswer(answer);
    currentQuestion.getSharedFields().setComplete(true);
  }

}
