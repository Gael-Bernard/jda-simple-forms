package fr.gbernard.jdaforms.controller.defaultmessages;

import fr.gbernard.jdaforms.model.Form;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.function.Function;

public class DefaultMessageCreateDatas {

  public static Function<Form, MessageCreateData> timeoutMessage() {
    return form -> MessageCreateData.fromContent("❌ Your form was cancelled due to the 20 minute timeout.");
  }

}
