package fr.gbernard.jdaforms.service;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.FormAnswersMap;
import fr.gbernard.jdaforms.model.Question;
import fr.gbernard.jdaforms.repository.OngoingFormsRepository;
import fr.gbernard.jdaforms.utils.ExceptionUtils;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FormContinueService {

  final OngoingFormsRepository ongoingFormsRepository = new OngoingFormsRepository();

  public void setAnswerInForm(Form form, List<String> answer) {
    form.getCurrentQuestion()
        .orElseThrow(IllegalStateException::new)
        .parseAndSetAnswer(answer);
  }

  public void sendCurrentQuestionOrEnd(Form form, InteractionHook hook) {
    if(form.getCurrentQuestion().isPresent()) {
      ExceptionUtils.uncheck(() -> form.getCurrentQuestion().get().getMessageEditor().edit(hook, form) );
    }
    else {
      this.triggerFormComplete(form, hook);
    }
  }

  public void triggerFormComplete(Form form, InteractionHook hook) {

    final Map<String, Object> answersMap = form.getQuestions().stream()
        .filter(question -> question.getAnswer().isPresent())
        .collect(Collectors.toMap(Question::getKey, q -> q.getAnswer().get()));
    form.getOnFormComplete().accept( new FormAnswersMap(answersMap), form);

    ExceptionUtils.uncheck(() -> form.getFinalMessage().edit(hook, form) );
  }

  public void saveOrDeleteForm(Form form) {
    if(form.getCurrentQuestion().isPresent()) {
      ongoingFormsRepository.save(form);
    }
    else {
      ongoingFormsRepository.delete(form);
    }
  }

  public static void updateFormToNextQuestion(Form form) {
    form.setCurrentQuestion( getNextQuestion(form) );
  }

  public static Optional<Question<?>> getNextQuestion(Form form) {

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

    Optional<Question<?>> nextQuestionInOrder = getNextQuestionInOrder(form);
    if(nextQuestionInOrder.isPresent()) {
      return nextQuestionInOrder;
    }

    return Optional.empty();
  }

  private static Optional<Question<?>> getNextQuestionInOrder(Form form) {
    if(form.getCurrentQuestion().isEmpty()) {
      return Optional.empty();
    }

    final List<Question<?>> questions = form.getQuestions();
    final int currentQuestionIndex = questions.indexOf( form.getCurrentQuestion().get() );
    if(currentQuestionIndex+1 == questions.size()) {
      return Optional.empty();
    }

    return Optional.of( questions.get(currentQuestionIndex+1) );
  }

}
