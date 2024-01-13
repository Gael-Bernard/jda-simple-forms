package examplebot.controller.form;

import examplebot.controller.dropdownitem.Vegetable;
import fr.gbernard.jdaforms.controller.question.dropdown.*;
import fr.gbernard.jdaforms.model.Form;

import java.util.List;

public class AllDropdownQuestionsForm {

  public static Form createForm() {

    final CustomDropdownQuestion<Vegetable> customDropdown = new CustomDropdownBuilder<Vegetable>()
        .key("favourite-vegetable")
        .title("What are your 2-3 favourite vegetables?")
        .subtitle("We hope you eat vegetables on a daily basis for your health!")
        .choices( List.of(Vegetable.values()) )
        .minSelectedItems(2)
        .maxSelectedItems(3)
        .parser(Vegetable::parse)
        .build();

    final UserDropdownQuestion userDropdown = new UserDropdownBuilder()
        .key("most-famous")
        .title("Who's the most famous here?")
        .subtitle("From your perspective, who is the most famous person in the guild?")
        .build();

    final ChannelDropdownQuestion channelDropdown = new ChannelDropdownBuilder()
        .key("easter-eggs-location")
        .title("Pick where you want to leave the Easter Egg")
        .subtitle("The Easter Egg will be hidden in one of the guild's channels!")
        .minSelectedItems(2)
        .maxSelectedItems(2)
        .build();

    final RoleDropdownQuestion roleDropdown = new RoleDropdownBuilder()
        .key("gift")
        .title("Ask for a role as Christmas gift!")
        .subtitle("It's Christmas, which role would you like to get? (I can't promise you're getting it!)")
        .minSelectedItems(1)
        .maxSelectedItems(1)
        .build();

    return Form.builder()
        .mandatoryQuestions(List.of(customDropdown, userDropdown, channelDropdown, roleDropdown))
        .build();
  }

}
