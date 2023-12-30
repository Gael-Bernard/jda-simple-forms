package fr.gbernard.jdaforms.service;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import mocks.service.FormMocks;
import mocks.service.QuestionMocks;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.gbernard.jdaforms.service.FormContinueService.GoToNextQuestionResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FormContinueServiceTest_goToNextQuestion {

  Form firstQuestionWithoutSubquestionsForm() {
    return FormMocks.initialised2YesNoQuestions();
  }

  Form firstQuestionWithNestedSubquestionsForm() {
    Form form = FormMocks.initialised2YesNoQuestions();
    form.setQuestions(List.of(QuestionMocks.nestedSubquestions1(), QuestionMocks.nestedSubquestions2()));
    Question<?> newQuestion1 = form.getQuestions().get(0);
    form.setCurrentQuestion(newQuestion1);
    return form;
  }

  Form lastQuestionForm() {
    Form form = FormMocks.initialised2YesNoQuestions();
    Question<?> lastQuestion = form.getQuestions().get(1);
    form.setCurrentQuestion( lastQuestion );
    return form;
  }

  @Test
  void noMoreQuestionReturnsNoMoreQuestion() {
    Form form = lastQuestionForm();
    assertEquals( GoToNextQuestionResult.NO_MORE_QUESTION, FormContinueService.goToNextQuestion(form) );
  }

  @Test
  void noMoreQuestionSetsCurrentQuestionToNull() {
    Form form = lastQuestionForm();
    FormContinueService.goToNextQuestion(form);
    assertEquals(null, form.getCurrentQuestion() );
  }

  @Test
  void noSubquestionSetsCurrentQuestionToNext() {
    Form form = firstQuestionWithoutSubquestionsForm();
    Question<?> question2 = form.getQuestions().get(1);
    FormContinueService.getNextQuestion(form);
    assertEquals(question2, question2);
  }

  @Test
  void subquestionSetsCurrentQuestionToSubquestion() {
    Form form = firstQuestionWithNestedSubquestionsForm();
    String subquestionKey = form.getQuestions().get(0).getOptionalNextQuestion().apply(form).get().getKey();
    FormContinueService.goToNextQuestion(form);
    assertEquals(subquestionKey, form.getCurrentQuestion().getKey());
  }

  @Test
  void subquestionSetsCurrentQuestionToNestedSubquestion() {
    Form form = firstQuestionWithNestedSubquestionsForm();
    Question<?> subquestion = form.getQuestions().get(0).getOptionalNextQuestion().apply(form).get();
    Question<?> nestedSubquestion = subquestion.getOptionalNextQuestion().apply(form).get();
    form.setCurrentQuestion(subquestion);
    FormContinueService.goToNextQuestion(form);
    assertNotEquals(subquestion.getKey(), nestedSubquestion.getKey(), "Test may give wrong result if subquestion and nested subquestion have same key");
    assertEquals(nestedSubquestion.getKey(), form.getCurrentQuestion().getKey());
  }

}