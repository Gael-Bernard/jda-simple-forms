package v2.builder.form.saver;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import v2.builder.form.response.ResponseBuilder;

import java.util.List;

@AllArgsConstructor
public class StringSelectSaveBuilder implements AnswerSaveBuilder<List<String>> {

  public ResponseBuilder<StringSelectInteractionEvent> saveStringsOnKey(String key) {
    throw new UnsupportedOperationException();
  }

}
