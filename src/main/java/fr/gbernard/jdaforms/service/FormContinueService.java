package fr.gbernard.jdaforms.service;

import fr.gbernard.jdaforms.exception.QuestionNotFoundException;
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

    final Map<String, Object> answersMap = form.getMandatoryQuestions().stream()
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
    Optional<Question<?>> nextQuestion = getNextQuestion(form);

    if(nextQuestion.isPresent()) {
      form.getQuestionsHistory().push(nextQuestion.get());
    }
    else {
      form.setComplete();
    }
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
    final List<Question<?>> mandatoryQuestions = form.getMandatoryQuestions();
    int latestMandatoryQuestionIndex = indexOflatestMandatoryQuestionInHistory(form);

    if(latestMandatoryQuestionIndex == mandatoryQuestions.size()-1) {
      return Optional.empty();
    }

    return Optional.of( mandatoryQuestions.get(latestMandatoryQuestionIndex+1) );
  }

  private static int indexOflatestMandatoryQuestionInHistory(Form form) {
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
