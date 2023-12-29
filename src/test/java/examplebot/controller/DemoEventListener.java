package examplebot.controller;

import fr.gbernard.jdaforms.controller.question.yesno.YesNoQuestion;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.service.FormStartService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.List;

public class DemoEventListener extends ListenerAdapter {

  private final FormStartService formStartService = new FormStartService();

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent commandEvent) {
    final String commandName = commandEvent.getName();

    /*
    Multiple Yes/No questions
    Ephemeral messages (only visible by answering user)
     */
    if(commandName.equals("testyesno")) {

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

      formStartService.startForm(commandEvent, form);

    }

    /*
    Single Yes/No question
    Non-ephemeral message (visible from everyone, default value)
     */
    else if(commandName.equals("testoneyesno")) {

      Form form = Form.builder()
          .questions(List.of(
              YesNoQuestion.builder()
                  .key("single-yes-no-question")
                  .title("Please press \"SHUT UP AND TAKE MY MONEY!\" now")
                  .yesLabel("SHUT UP AND TAKE MY MONEY!")
                  .noLabel("Not interested, sorry")
                  .build()
          ))
          .onFormComplete((answers, completeForm) -> {
            final boolean answer = answers.getAsBoolean("single-yes-no-question");
            System.out.println("User has selected button: "+answer);
          })
          .build();

      formStartService.startForm(commandEvent, form);

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
