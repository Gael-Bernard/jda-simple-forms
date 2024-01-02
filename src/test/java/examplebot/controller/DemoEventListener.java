package examplebot.controller;

import examplebot.controller.form.AllDropdownQuestionsForm;
import examplebot.controller.form.SingleYesNoQuestionForm;
import examplebot.controller.form.YesNoQuestionsForm;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.feature.FormStartFeature;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class DemoEventListener extends ListenerAdapter {

  private final FormStartFeature formStartFeature = new FormStartFeature();

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent commandEvent) {
    final String commandName = commandEvent.getName();

    // This command returns Ephemeral messages (only visible by answering user)
    if(commandName.equals("testyesno")) {
      Form form = YesNoQuestionsForm.createForm();
      formStartFeature.startForm(commandEvent, form);
    }

    else if(commandName.equals("testoneyesno")) {
      Form form = SingleYesNoQuestionForm.createForm();
      formStartFeature.startForm(commandEvent, form);
    }

    else if(commandName.equals("alldropdowns")) {
      Form form = AllDropdownQuestionsForm.createForm();
      formStartFeature.startForm(commandEvent, form);
    }

    /*
    Command not found
     */
    else {
      commandEvent
          .reply( MessageCreateData.fromEmbeds(EmbedTemplate.basic(
              "Error: Command not found",
              "A technical problem has occurred with the bot. Please retry later.",
              EmbedColor.ERROR)))
          .setEphemeral(true)
          .queue();
    }

  }
}
