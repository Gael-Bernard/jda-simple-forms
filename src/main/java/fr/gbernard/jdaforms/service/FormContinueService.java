package fr.gbernard.jdaforms.service;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.FormAnswersMap;
import fr.gbernard.jdaforms.model.Question;
import fr.gbernard.jdaforms.utils.ExceptionUtils;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FormContinueService {

  public void continueForm(Form form, List<String> answer, InteractionHook hook) {
    form.getCurrentQuestion().parseAndSetAnswer(answer);
    var nextQuestionStatus = goToNextQuestion(form);

    switch (nextQuestionStatus) {
      case NEXT_QUESTION:
        ExceptionUtils.uncheck(() -> form.getCurrentQuestion().editQuestionMessage(hook, form) );
        break;
      case NO_MORE_QUESTION:
        this.triggerFormComplete(form, hook);
        break;
    }
  }

  public static GoToNextQuestionResult goToNextQuestion(Form form) {
    Optional<Question<?>> nextQuestionOpt = getNextQuestion(form);

    if(nextQuestionOpt.isEmpty()) {
      form.setCurrentQuestion(null);
      return GoToNextQuestionResult.NO_MORE_QUESTION;
    }

    Question<?> nextQuestion = nextQuestionOpt.get();
    form.setCurrentQuestion(nextQuestion);
    return GoToNextQuestionResult.NEXT_QUESTION;
  }

  public static Optional<Question<?>> getNextQuestion(Form form) {
    Question<?> currentQuestion = form.getCurrentQuestion();

    final Optional<Question<?>> additionalSubquestion = currentQuestion.getOptionalNextQuestion().apply(form);
    if(additionalSubquestion.isPresent()) {
      return additionalSubquestion;
    }

    Optional<Question<?>> nextQuestionInOrder = getNextQuestionInOrder(form);
    if(nextQuestionInOrder.isPresent()) {
      return nextQuestionInOrder;
    }

    return Optional.empty();
  }

  private static Optional<Question<?>> getNextQuestionInOrder(Form form) {
    final List<Question<?>> questions = form.getQuestions();
    final int currentQuestionIndex = questions.indexOf( form.getCurrentQuestion() );
    if(currentQuestionIndex+1 == questions.size()) {
      return Optional.empty();
    }

    return Optional.of( questions.get(currentQuestionIndex+1) );
  }

  private static void triggerFormComplete(Form form, InteractionHook hook) {

    final Map<String, Object> answersMap = form.getQuestions().stream()
        .filter(question -> question.getAnswer().isPresent())
        .collect(Collectors.toMap(q -> q.getKey(), q -> q.getAnswer().get()));
    form.getOnFormComplete().accept( new FormAnswersMap(answersMap), form);

    EditMessage.text(hook, "✅ **DONE, THANK YOU!**");
    // TODO : Final summary
  }

  public enum GoToNextQuestionResult {
    NEXT_QUESTION,
    NO_MORE_QUESTION
  }

}
