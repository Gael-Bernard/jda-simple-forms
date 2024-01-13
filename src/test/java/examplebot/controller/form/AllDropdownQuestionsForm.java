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

    final CustomDropdownQuestion<Vegetable> customDropdown = new CustomDropdownQuestion<Vegetable>()
        .setSubtitle("We hope you eat vegetables on a daily basis for your health!")
        .setChoices( List.of(Vegetable.values()) )
        .setMinSelectedItems(2)
        .setMaxSelectedItems(3)
        .setParser( Vegetable::parse );
    customDropdown.getSharedFields()
        .setKey("favourite-vegetable")
        .setTitle("What are your 2-3 favourite vegetables?");

    final UserDropdownQuestion userDropdown = new UserDropdownQuestion()
        .setSubtitle("From your perspective, who is the most famous person in the guild?");
    userDropdown.getSharedFields()
        .setKey("most-famous")
        .setTitle("Who's the most famous here?");

    final ChannelDropdownQuestion channelDropdown = new ChannelDropdownQuestion()
        .setSubtitle("The Easter Egg will be hidden in one of the guild's channels!")
        .setMinSelectedItems(2)
        .setMaxSelectedItems(2);
    channelDropdown.getSharedFields()
        .setKey("easter-eggs-location")
        .setTitle("Pick where you want to leave the Easter Egg");

    final RoleDropdownQuestion roleDropdown = new RoleDropdownQuestion()
        .setSubtitle("It's Christmas, which role would you like to get? (I can't promise you're getting it!)")
        .setMinSelectedItems(1)
        .setMaxSelectedItems(1);
    roleDropdown.getSharedFields()
        .setKey("gift")
        .setTitle("Ask for a role as Christmas gift!");

    return Form.builder()
        .mandatoryQuestions(List.of(customDropdown, userDropdown, channelDropdown, roleDropdown))
        .build();
  }

}
