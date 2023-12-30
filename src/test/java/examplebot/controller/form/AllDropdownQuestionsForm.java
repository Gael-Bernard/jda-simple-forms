package examplebot.controller.form;

import examplebot.controller.dropdownitem.Vegetable;
import fr.gbernard.jdaforms.controller.question.dropdown.CustomDropdownQuestion;
import fr.gbernard.jdaforms.model.Form;

import java.util.List;

public class AllDropdownQuestionsForm {

  public static Form createForm() {
    return Form.builder()
        .questions(List.of(

            CustomDropdownQuestion.builder()
                .key("favourite-vegetable")
                .title("What is your favourite vegetable?")
                .subtitle("We hope you eat vegetables on a daily basis for your health!")
                .choices( List.of(Vegetable.values()) )
                .parser( Vegetable::parse )
                .build()

        ))
        .build();
  }

}
