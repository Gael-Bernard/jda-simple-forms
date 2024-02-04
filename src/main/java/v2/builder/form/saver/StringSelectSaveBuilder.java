package v2.builder.form.saver;

import lombok.AllArgsConstructor;
import v2.builder.form.response.StringSelectResponseBuilder;

import java.util.List;

@AllArgsConstructor
public class StringSelectSaveBuilder implements AnswerSaveBuilder<List<String>> {

  public StringSelectResponseBuilder saveStringsOnKey(String key) {
    throw new UnsupportedOperationException();
  }

}
