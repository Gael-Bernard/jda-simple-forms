package examplebot.controller.form;

import examplebot.controller.dropdownitem.Vegetable;
import fr.gbernard.jdaforms.controller.question.dropdown.ChannelDropdownQuestion;
import fr.gbernard.jdaforms.controller.question.dropdown.CustomDropdownQuestion;
import fr.gbernard.jdaforms.controller.question.dropdown.RoleDropdownQuestion;
import fr.gbernard.jdaforms.controller.question.dropdown.UserDropdownQuestion;
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
                .build(),

            UserDropdownQuestion.builder()
                .key("most-famous")
                .title("Who's the most famous here?")
                .subtitle("From your perspective, who is the most famous person in the guild?")
                .build(),

            ChannelDropdownQuestion.builder()
                .key("easter-eggs-location")
                .title("Pick where you want to leave the Easter Egg")
                .subtitle("The Easter Egg will be hidden in one of the guild's channels!")
                .build(),

            RoleDropdownQuestion.builder()
                .key("gift")
                .title("Ask for a role as Christmas gift!")
                .subtitle("It's Christmas, which role would you like to get? (I can't promise you're getting it!)")
                .build()

        ))
        .build();
  }

}
