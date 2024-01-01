package fr.gbernard.jdaforms.business;

import fr.gbernard.jdaforms.exception.QuestionNotFoundException;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;

import java.util.List;
import java.util.Optional;

public class FormNextQuestionBusiness {

  public void updateFormToNextQuestion(Form form) {
    Optional<Question<?>> nextQuestion = getNextQuestion(form);

    if(nextQuestion.isPresent()) {
      form.getQuestionsHistory().push(nextQuestion.get());
    }
    else {
      form.setComplete();
    }
  }

  public Optional<Question<?>> getNextQuestion(Form form) {

    Question<?> currentQuestion;
    {
      Optional<Question<?>> questionOpt = form.getCurrentQuestion();
      if(questionOpt.isEmpty()) { return Optional.empty(); }
      currentQuestion = questionOpt.get();
    }

    final Optional<Question<?>> additionalSubquestion = currentQuestion.getOptionalNextQuestion().apply(form);
    if(additionalSubquestion.isPresent()) {
      return additionalSubquestion;
    }

    Optional<Question<?>> nextQuestionInOrder = getNextMandatoryQuestion(form);
    if(nextQuestionInOrder.isPresent()) {
      return nextQuestionInOrder;
    }

    return Optional.empty();
  }

  private Optional<Question<?>> getNextMandatoryQuestion(Form form) {
    final List<Question<?>> mandatoryQuestions = form.getMandatoryQuestions();
    int latestMandatoryQuestionIndex = indexOfLatestMandatoryQuestionInHistory(form);

    if(latestMandatoryQuestionIndex == mandatoryQuestions.size()-1) {
      return Optional.empty();
    }

    return Optional.of( mandatoryQuestions.get(latestMandatoryQuestionIndex+1) );
  }

  private int indexOfLatestMandatoryQuestionInHistory(Form form) {
    Question<?> candidateQuestion;
    boolean isMandatoryQuestion;
    int mandatoryIndex, historyIndex = form.getQuestionsHistory().size();

    do {
      historyIndex--;
      candidateQuestion = form.getQuestionsHistory().get(historyIndex);
      mandatoryIndex = form.getMandatoryQuestions().indexOf(candidateQuestion);
      isMandatoryQuestion = mandatoryIndex != -1;
      if(isMandatoryQuestion) {
        return mandatoryIndex;
      }
    }
    while(historyIndex > 0);

    throw new QuestionNotFoundException("No question in the questions history is a mandatory question");
  }

}
