package fr.gbernard.jdaforms.service;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import mocks.service.FormMocks;
import mocks.service.QuestionMocks;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;

class FormContinueServiceTest_updateFormToNextQuestion {

  Form firstQuestionWithoutSubquestionsForm() {
    return FormMocks.initialised2YesNoQuestions();
  }

  Form firstQuestionWithNestedSubquestionsForm() {
    Form form = FormMocks.initialised2YesNoQuestions();
    form.setQuestions(List.of(QuestionMocks.nestedSubquestions1(), QuestionMocks.nestedSubquestions2()));
    Question<?> newQuestion1 = form.getQuestions().get(0);
    form.setCurrentQuestion( of(newQuestion1) );
    return form;
  }

  Form lastQuestionForm() {
    Form form = FormMocks.initialised2YesNoQuestions();
    Question<?> lastQuestion = form.getQuestions().get(1);
    form.setCurrentQuestion( of(lastQuestion) );
    return form;
  }

  @Test
  void noMoreQuestionSetsNoEmptyQuestion() {
    Form form = lastQuestionForm();
    FormContinueService.updateFormToNextQuestion(form);
    assertTrue(form.getCurrentQuestion().isEmpty());
  }

  @Test
  void noMoreQuestionSetsCurrentQuestionToNull() {
    Form form = lastQuestionForm();
    FormContinueService.updateFormToNextQuestion(form);
    assertTrue( form.getCurrentQuestion().isEmpty() );
  }

  @Test
  void noSubquestionSetsCurrentQuestionToNext() {
    Form form = firstQuestionWithoutSubquestionsForm();
    Question<?> question2 = form.getQuestions().get(1);
    FormContinueService.updateFormToNextQuestion(form);
    assertEquals(question2, form.getCurrentQuestion().get());
  }

  @Test
  void subquestionSetsCurrentQuestionToSubquestion() {
    Form form = firstQuestionWithNestedSubquestionsForm();
    String subquestionKey = form.getQuestions().get(0).getOptionalNextQuestion().apply(form).get().getKey();
    FormContinueService.updateFormToNextQuestion(form);
    assertEquals(subquestionKey, form.getCurrentQuestion().get().getKey());
  }

  @Test
  void subquestionSetsCurrentQuestionToNestedSubquestion() {
    Form form = firstQuestionWithNestedSubquestionsForm();
    Question<?> subquestion = form.getQuestions().get(0).getOptionalNextQuestion().apply(form).get();
    Question<?> nestedSubquestion = subquestion.getOptionalNextQuestion().apply(form).get();
    form.setCurrentQuestion( of(subquestion) );
    FormContinueService.updateFormToNextQuestion(form);
    assertNotEquals(subquestion.getKey(), nestedSubquestion.getKey(), "Test may give wrong result if subquestion and nested subquestion have same key");
    assertEquals(nestedSubquestion.getKey(), form.getCurrentQuestion().get().getKey());
  }

}