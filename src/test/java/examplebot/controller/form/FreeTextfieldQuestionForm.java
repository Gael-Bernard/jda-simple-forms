package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.question.freetextfield.FreeTextFieldQuestion;
import fr.gbernard.jdaforms.model.Form;

import java.util.List;

public class FreeTextfieldQuestionForm {

  public static Form createForm() {

    final FreeTextFieldQuestion question1 = new FreeTextFieldQuestion()
        .setModalTitle("What's your name?")
        .setFieldLabel("Your name");
    question1.getSharedFields()
        .setKey("name");

    final FreeTextFieldQuestion question2 = new FreeTextFieldQuestion()
        .setModalTitle("Mystery city location")
        .setFieldLabel("Mystery city location")
        .setFieldPlaceholder("Name here");
    question2.getSharedFields()
        .setTitle("State a city name and discover who knows its location!")
        .setKey("mystery-city");

    return Form.builder()
        .mandatoryQuestions(List.of(question1, question2))
        .build();
  }

}
