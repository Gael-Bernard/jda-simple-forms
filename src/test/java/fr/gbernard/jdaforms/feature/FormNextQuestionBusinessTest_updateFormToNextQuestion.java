package fr.gbernard.jdaforms.feature;

import fr.gbernard.jdaforms.business.FormNextQuestionBusiness;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import mocks.service.FormMocks;
import mocks.service.QuestionMocks;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormNextQuestionBusinessTest_updateFormToNextQuestion {

  final FormNextQuestionBusiness formNextQuestionBusiness = new FormNextQuestionBusiness();

  Form firstQuestionWithoutSubquestionsForm() {
    return FormMocks.initialised2YesNoQuestions();
  }

  Form atQuestion1SubquestionForm() {
    Form form = FormMocks.initialised2YesNoQuestions();
    form.setMandatoryQuestions(List.of(QuestionMocks.simpleSubquestion1(), QuestionMocks.simpleSubquestion2()));
    Question<?> firstMandatoryQuestion = form.getMandatoryQuestions().get(0);
    Question<?> firstSubquestion = firstMandatoryQuestion.getOptionalNextQuestion().apply(form).get();
    form.getQuestionsHistory().push(firstMandatoryQuestion);
    form.getQuestionsHistory().push(firstSubquestion);
    return form;
  }

  Form firstQuestionWithNestedSubquestionsForm() {
    Form form = FormMocks.initialised2YesNoQuestions();
    form.setMandatoryQuestions(List.of(QuestionMocks.nestedSubquestions1(), QuestionMocks.nestedSubquestions2()));
    Question<?> newQuestion1 = form.getMandatoryQuestions().get(0);
    form.getQuestionsHistory().push(newQuestion1);
    return form;
  }

  Form lastQuestionForm() {
    Form form = FormMocks.initialised2YesNoQuestions();
    Question<?> firstQuestion = form.getMandatoryQuestions().get(0);
    Question<?> lastQuestion = form.getMandatoryQuestions().get(1);
    form.getQuestionsHistory().push(firstQuestion);
    form.getQuestionsHistory().push(lastQuestion);
    return form;
  }

  @Test
  void noMoreQuestionSetsNoEmptyQuestion() {
    Form form = lastQuestionForm();
    formNextQuestionBusiness.updateFormToNextQuestion(form);
    assertTrue(form.getCurrentQuestion().isEmpty());
  }

  @Test
  void noMoreQuestionSetsCurrentQuestionToNull() {
    Form form = lastQuestionForm();
    formNextQuestionBusiness.updateFormToNextQuestion(form);
    assertTrue( form.getCurrentQuestion().isEmpty() );
  }

  @Test
  void noSubquestionSetsCurrentQuestionToNext() {
    Form form = firstQuestionWithoutSubquestionsForm();
    Question<?> question2 = form.getMandatoryQuestions().get(1);
    formNextQuestionBusiness.updateFormToNextQuestion(form);
    assertEquals(question2, form.getCurrentQuestion().get());
  }

  @Test
  void subquestionSetsCurrentQuestionToSubquestion() {
    Form form = firstQuestionWithNestedSubquestionsForm();
    String subquestionKey = form.getMandatoryQuestions().get(0).getOptionalNextQuestion().apply(form).get().getKey();
    formNextQuestionBusiness.updateFormToNextQuestion(form);
    assertEquals(subquestionKey, form.getCurrentQuestion().get().getKey());
  }

  @Test
  void subquestionSetsCurrentQuestionToNestedSubquestion() {
    Form form = firstQuestionWithNestedSubquestionsForm();
    Question<?> subquestion = form.getMandatoryQuestions().get(0).getOptionalNextQuestion().apply(form).get();
    Question<?> nestedSubquestion = subquestion.getOptionalNextQuestion().apply(form).get();
    form.getQuestionsHistory().push( form.getMandatoryQuestions().get(0) );
    form.getQuestionsHistory().push( subquestion );
    formNextQuestionBusiness.updateFormToNextQuestion(form);
    assertNotEquals(subquestion.getKey(), nestedSubquestion.getKey(), "Test may give wrong result if subquestion and nested subquestion have same key");
    assertEquals(nestedSubquestion.getKey(), form.getCurrentQuestion().get().getKey());
  }

  @Test
  void noMoreSubquestionSetsCurrentQuestionToNextMandatory() {
    Form form = atQuestion1SubquestionForm();
    Question<?> mandatoryQuestion2 = form.getMandatoryQuestions().get(1);
    formNextQuestionBusiness.updateFormToNextQuestion(form);
    assertEquals(mandatoryQuestion2, form.getCurrentQuestion().get());
  }

}