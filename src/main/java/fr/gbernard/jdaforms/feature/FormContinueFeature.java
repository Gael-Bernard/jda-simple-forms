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
import net.dv8tion.jda.api.interactions.callbacks.IMessageEditCallback;
import net.dv8tion.jda.api.interactions.components.ComponentInteraction;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FormContinueFeature {

  final FormNextQuestionBusiness formNextQuestionBusiness = new FormNextQuestionBusiness();
  final QuestionCompletionBusiness questionCompletionBusiness = new QuestionCompletionBusiness();
  final OngoingFormsRepository ongoingFormsRepository = new OngoingFormsRepository();

  public Optional<Modal> getCurrentQuestionModalIfAny(Form form, List<String> discordReturnedValues) {
    Question<?> currentQuestion = form.getCurrentQuestion()
        .orElseThrow(() -> new NoCurrentQuestionException("Cannot trigger get current question's modal if there is no current question"));

    return currentQuestion.getModalProviderInsteadOfHandler().getOptionalModal(discordReturnedValues, form);
  }

  public void triggerCurrentQuestionInteractionHandler(ComponentInteraction message, List<String> answers, Form form) {
    Question<?> currentQuestion = form.getCurrentQuestion()
        .orElseThrow(() -> new NoCurrentQuestionException("Cannot trigger FormInteractionHandler if there is no current question"));

    QuestionActions actions = QuestionActions.builder()
        .formContinueFeature(this)
        .questionCompletionBusiness(questionCompletionBusiness)
        .form(form)
        .message(message)
        .build();

    currentQuestion.getFormInteractionHandler().handle(answers, actions);
  }

  public <T> void saveAnswerAndSendNextQuestion(InteractionHook hookTomessage, Form form, T answer) {
    Question<T> question = (Question<T>) form.getCurrentQuestion()
        .orElseThrow((() -> new NoCurrentQuestionException("Cannot save answer if there is no current question")));
    questionCompletionBusiness.completeWithAnswer(question, answer);
    sendNextQuestion(hookTomessage, form);
  }

  public void sendNextQuestion(InteractionHook hookTomessage, Form form) {
    formNextQuestionBusiness.updateFormToNextQuestion(form);
    sendCurrentQuestionOrEnd(hookTomessage, form);
  }

  private void sendCurrentQuestionOrEnd(InteractionHook hookTomessage, Form form) {
    Optional<Question<?>> question = form.getCurrentQuestion();
    if(question.isPresent()) {
      this.refreshFormWithQuestion(hookTomessage, form, question.get());
    }
    else {
      this.triggerFormComplete(form, hookTomessage);
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

  public void cancelForm(Form form) {
    form.setComplete();
    ongoingFormsRepository.delete(form);
  }

  public void refreshFormWithCurrentQuestion(IMessageEditCallback message, Form form) {
    final Question<?> currentQuestion = form.getCurrentQuestion()
        .orElseThrow(() -> new NoCurrentQuestionException("Cannot refresh current question because there is no current question"));

    message.deferEdit().queue();
    refreshFormWithQuestion(message.getHook(), form, currentQuestion);
  }

  public void refreshFormWithQuestion(InteractionHook hookTomessage, Form form, Question<?> question) {
    ExceptionUtils.uncheck(() -> question.getMessageEditor().edit(hookTomessage, form) );
  }

  public void triggerFormComplete(Form form, InteractionHook hookTomessage) {

    final Map<String, Object> answersMap = form.getQuestionsHistory().stream()
        .filter(question -> question.getAnswer().isPresent())
        .collect(Collectors.toMap(Question::getKey, q -> q.getAnswer().get()));
    form.getOnFormComplete().accept( new FormAnswersMap(answersMap), form);

    ExceptionUtils.uncheck(() -> form.getFinalMessage().edit(hookTomessage, form) );
  }

}
