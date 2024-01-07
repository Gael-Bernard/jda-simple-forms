package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.question.freetextfield.FreeTextFieldQuestion;
import fr.gbernard.jdaforms.model.Form;

import java.util.List;

public class FreeTextfieldQuestionForm {

  public static Form createForm() {

    final FreeTextFieldQuestion question1 = FreeTextFieldQuestion.builder()
        .key("name")
        .modalTitle("What's your name?")
        .fieldTitle("Your name")
        .build();

    final FreeTextFieldQuestion question2 = FreeTextFieldQuestion.builder()
        .key("mystery-city")
        .instructionsTitle("State a city name and discover who knows its location!")
        .modalTitle("Mystery city location")
        .fieldTitle("Mystery city location")
        .fieldPlaceholder("Name here")
        .build();


    return Form.builder()
        .mandatoryQuestions(List.of(question1, question2))
        .build();
  }

}
