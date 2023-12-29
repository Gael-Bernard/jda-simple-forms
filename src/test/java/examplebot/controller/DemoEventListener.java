package examplebot.controller;

import examplebot.DemoApplication;
import fr.gbernard.jdaforms.controller.messagedata.EmbedColor;
import fr.gbernard.jdaforms.controller.messagedata.EmbedMessageDataGenerator;
import fr.gbernard.jdaforms.controller.question.yesno.YesNoQuestion;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.service.FormStartService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class DemoEventListener extends ListenerAdapter {

  FormStartService formStartService = new FormStartService();

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

    if(event.getName().equals(DemoApplication.TEST_X_YES_NO_COMMANDNAME)) {

      Form form = Form.builder()
          .questions(List.of(

              YesNoQuestion.builder()
                  .key("accept-fill-form")
                  .title("Do you want to continue?")
                  .build(),

              YesNoQuestion.builder()
                  .key("like-jdasf")
                  .title("Do you like the JDA Simple Forms?")
                  .subtitle("Don't worry, the dev will never know what you answered ;)")
                  .build(),

              YesNoQuestion.builder()
                  .key("healthy_food")
                  .title("Do you eat healthily? :medical_symbol:")
                  .build()

          )).ephemeral(true).build();

      formStartService.startForm(event, form);

    }

    else {
      event
          .reply(EmbedMessageDataGenerator.basicCreate(
              "Error: Command not found",
              "A technical problem has occurred with the bot. Please retry later.",
              EmbedColor.ERROR))
          .setEphemeral(true)
          .queue();
    }

  }
}
