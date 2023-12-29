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

    Optional<Question<?>> nextQuestionOpt = this.getNextQuestion(form);
    if(nextQuestionOpt.isPresent()) {
      Question<?> nextQuestion = nextQuestionOpt.get();
      form.setCurrentQuestion(nextQuestion);
      ExceptionUtils.uncheck(() -> nextQuestion.editQuestionMessage(hook, form) );
    }
    else {
      this.triggerFormComplete(form, hook);
    }
  }

  public Optional<Question<?>> getNextQuestion(Form form) {
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

  private Optional<Question<?>> getNextQuestionInOrder(Form form) {
    final List<Question<?>> questions = form.getQuestions();
    final int currentQuestionIndex = questions.indexOf( form.getCurrentQuestion() );
    if(currentQuestionIndex+1 == questions.size()) {
      return Optional.empty();
    }

    return Optional.of( questions.get(currentQuestionIndex+1) );
  }

  private void triggerFormComplete(Form form, InteractionHook hook) {

    final Map<String, Object> answersMap = form.getQuestions().stream()
        .filter(question -> question.getAnswer().isPresent())
        .collect(Collectors.toMap(q -> q.getKey(), q -> q.getAnswer().get()));
    form.getOnFormComplete().accept( new FormAnswersMap(answersMap), form);

    EditMessage.text(hook, "✅ **DONE, THANK YOU!**");
    // TODO : Final summary
  }

}
