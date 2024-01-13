package fr.gbernard.jdaforms.business;

import fr.gbernard.jdaforms.exception.NoCurrentQuestionException;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;

import java.util.Optional;

public class QuestionCompletionBusiness {

  public <T> void setAnswerOfCurrentQuestion(Form form, T answer) {
    final Question<T> currentQuestion = (Question<T>) form.getCurrentQuestion()
        .orElseThrow(() -> new NoCurrentQuestionException("Cannot set current question answer because there is no current question"));

    currentQuestion.getSharedFields().setAnswer(answer);
  }

  public <T> void completeWithAnswer(Question<T> currentQuestion, T answer) {
    currentQuestion.getSharedFields().setAnswer(answer);
    currentQuestion.getSharedFields().setComplete(true);
  }

}
