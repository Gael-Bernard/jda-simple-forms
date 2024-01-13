package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.question.freetextfield.FreeTextFieldBuilder;
import fr.gbernard.jdaforms.controller.question.freetextfield.FreeTextFieldQuestion;
import fr.gbernard.jdaforms.model.Form;

import java.util.List;

public class FreeTextfieldQuestionForm {

  public static Form createForm() {

    final FreeTextFieldQuestion question1 = new FreeTextFieldBuilder()
        .key("name")
        .modalTitle("What's your name?")
        .fieldLabel("Your name")
        .build();

    final FreeTextFieldQuestion question2 = new FreeTextFieldBuilder()
        .key("mystery-city")
        .title("State a city name and discover who knows its location!")
        .modalTitle("Mystery city location")
        .fieldLabel("Mystery city location")
        .fieldPlaceholder("Name here")
        .build();

    return Form.builder()
        .mandatoryQuestions(List.of(question1, question2))
        .build();
  }

}
