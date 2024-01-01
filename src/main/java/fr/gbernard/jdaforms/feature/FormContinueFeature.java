package fr.gbernard.jdaforms.feature;

import fr.gbernard.jdaforms.business.FormNextQuestionBusiness;
import fr.gbernard.jdaforms.business.QuestionCompletionBusiness;
import fr.gbernard.jdaforms.exception.NoCurrentQuestionException;
import fr.gbernard.jdaforms.feature.proxy.QuestionActions;
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

public class FormContinueFeature {

  final FormNextQuestionBusiness formNextQuestionBusiness = new FormNextQuestionBusiness();
  final QuestionCompletionBusiness questionCompletionBusiness = new QuestionCompletionBusiness();
  final OngoingFormsRepository ongoingFormsRepository = new OngoingFormsRepository();

  public void triggerCurrentQuestionInteractionHandler(List<String> answers, InteractionHook hook, Form form) {
    Question<?> currentQuestion = form.getCurrentQuestion()
        .orElseThrow(() -> new NoCurrentQuestionException("Cannot trigger FormInteractionHandler if there is no current question"));

    QuestionActions actions = QuestionActions.builder()
        .formContinueFeature(this)
        .questionCompletionBusiness(questionCompletionBusiness)
        .form(form)
        .hook(hook)
        .build();

    currentQuestion.getFormInteractionHandler().handle(answers, actions);
  }

  public <T> void saveAnswerAndSendNextQuestion(InteractionHook hook, Form form, T answer) {
    Question<T> question = (Question<T>) form.getCurrentQuestion()
        .orElseThrow((() -> new NoCurrentQuestionException("Cannot save answer if there is no current question")));
    questionCompletionBusiness.completeWithAnswer(question, answer);
    sendNextQuestion(hook, form);
  }

  public void sendNextQuestion(InteractionHook hook, Form form) {
    formNextQuestionBusiness.updateFormToNextQuestion(form);
    sendCurrentQuestionOrEnd(hook, form);
  }

  private void sendCurrentQuestionOrEnd(InteractionHook hook, Form form) {
    Optional<Question<?>> question = form.getCurrentQuestion();
    if(question.isPresent()) {
      this.refreshFormWithQuestion(hook, form, question.get());
    }
    else {
      this.triggerFormComplete(form, hook);
    }
  }

  public void saveOrDeleteForm(Form form) {
    if(form.getCurrentQuestion().isPresent()) {
      ongoingFormsRepository.save(form);
    }
    else {
      ongoingFormsRepository.delete(form);
    }
  }

  public void refreshFormWithCurrentQuestion(InteractionHook hook, Form form) {
    final Question<?> currentQuestion = form.getCurrentQuestion()
        .orElseThrow(() -> new NoCurrentQuestionException("Cannot refresh current question because there is no current question"));

    refreshFormWithQuestion(hook, form, currentQuestion);
  }

  public void refreshFormWithQuestion(InteractionHook hook, Form form, Question<?> question) {
    ExceptionUtils.uncheck(() -> question.getMessageEditor().edit(hook, form) );
  }

  public void triggerFormComplete(Form form, InteractionHook hook) {

    final Map<String, Object> answersMap = form.getQuestionsHistory().stream()
        .filter(question -> question.getAnswer().isPresent())
        .collect(Collectors.toMap(Question::getKey, q -> q.getAnswer().get()));
    form.getOnFormComplete().accept( new FormAnswersMap(answersMap), form);

    ExceptionUtils.uncheck(() -> form.getFinalMessage().edit(hook, form) );
  }

}
