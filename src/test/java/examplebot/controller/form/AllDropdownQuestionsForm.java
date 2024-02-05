package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.question.dropdown.ChannelDropdownBuilder;
import fr.gbernard.jdaforms.controller.question.dropdown.ChannelDropdownQuestion;
import fr.gbernard.jdaforms.controller.question.dropdown.RoleDropdownBuilder;
import fr.gbernard.jdaforms.controller.question.dropdown.RoleDropdownQuestion;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import v2.builder.form.FormBuilder;
import v2.builder.form.question.StringSelectQuestionBuilder;
import v2.builder.form.question.UserSelectQuestionBuilder;
import v2.model.Form;

public class AllDropdownQuestionsForm {

  public static Form createForm() {

    final StringSelectQuestionBuilder customDropdown = new StringSelectQuestionBuilder();
    final UserSelectQuestionBuilder userDropdown = new UserSelectQuestionBuilder();

    /*
    final CustomDropdownQuestion<Vegetable> customDropdown = new CustomDropdownBuilder<Vegetable>()
        .title("What are your 2-3 favourite vegetables?")
        .subtitle("We hope you eat vegetables on a daily basis for your health!")
        .choices( List.of(Vegetable.values()) )
        .minSelectedItems(2)
        .maxSelectedItems(3)
        .parser(Vegetable::parse)
        .build();

    final UserDropdownQuestion userDropdown = new UserDropdownBuilder()
        .title("Who's the most famous here?")
        .subtitle("From your perspective, who is the most famous person in the guild?")
        .build();

     */

    final ChannelDropdownQuestion channelDropdown = new ChannelDropdownBuilder()
        .title("Pick where you want to leave the Easter Egg")
        .subtitle("The Easter Egg will be hidden in one of the guild's channels!")
        .minSelectedItems(2)
        .maxSelectedItems(2)
        .build();

    final RoleDropdownQuestion roleDropdown = new RoleDropdownBuilder()
        .title("Ask for a role as Christmas gift!")
        .subtitle("It's Christmas, which role would you like to get? (I can't promise you're getting it!)")
        .minSelectedItems(1)
        .maxSelectedItems(1)
        .build();

    return FormBuilder.create()
        .firstQuestion(customDropdown) // StringSelectSaveBuilder
        .saveStringsOnKey("favourite-vegetable") // StringSelectResponseBuilder
        .nextQuestion(userDropdown) // UserSelectSaveBuilder
        .saveUsersOnKey("most-famous") // EntitySelectResponseBuilder

        /*
        .nextQuestion(channelDropdown) // ChannelSelectSaveBuilder
        .saveChannelsOnKey("easter-eggs-location") // EntitySelectResponseBuilder
        .nextQuestion(roleDropdown) // RoleSelectSaveBuilder
        .saveRolesOnKey("gift") // EntitySelectResponseBuilder */

        .onFormComplete((EntitySelectInteractionEvent event, Form form) -> System.out.println(form)); // Form

  }

}
