package examplebot;

import examplebot.controller.DemoEventListener;
import examplebot.service.PropertiesService;
import fr.gbernard.jdaforms.controller.JdaFormsEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.List;

public class DemoApplication {

  private static final PropertiesService propertiesService = new PropertiesService();

  public static void main(String... args) throws InterruptedException {
    final String token = propertiesService.getProperty("TOKEN");
    final List<GatewayIntent> gatewayIntents = List.of(
        GatewayIntent.DIRECT_MESSAGES
    );

    JDA jda = JDABuilder
        .create(token, gatewayIntents)
        .addEventListeners(new JdaFormsEventListener())
        .addEventListeners(new DemoEventListener())
        .build();

    jda.updateCommands().addCommands(
        Commands.slash("testoneyesno", "Triggers a single Yes/No question"),
        Commands.slash("testyesno", "Triggers multiple Yes/No questions in a row"),
        Commands.slash("alldropdowns", "Triggers a form containing all the existing dropdowns")
    ).queue();

    jda.awaitReady();
  }

}
