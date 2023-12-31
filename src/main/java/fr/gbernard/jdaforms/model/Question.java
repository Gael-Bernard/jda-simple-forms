package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.utils.ExceptionUtils;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Question to ask the user
 * @param <T> type of the answer
 */
public interface Question<T> {

  /**
   * Unique key used to find the question and its answer in the form
   */
  @NonNull String getKey();
  /**
   * Unique key used to find the question and its answer in the form
   */
  void setKey(@NonNull String key);

  /**
   * Parsed answer given by the user if any
   */
  @NonNull Optional<T> getAnswer();
  /**
   * Parsed answer given by the user if any
   */
  void setAnswer(@NonNull Optional<T> answer);

  /**
   * Provider of the current question
   */
  @NonNull Function<Form, Optional<Question<?>>> getOptionalNextQuestion();
  void setOptionalNextQuestion(@NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion);

  /**
   * Function that edits the form message to display this question<br>
   */
  FormMessageEditor getMessageEditor();

  /**
   * Converts the Strings received from Discord to the right type if necessary
   * @param discordReturnedValues string values received from Discord
   * @return converted value
   */
  T parseAnswer(List<String> discordReturnedValues) throws Exception;

  /**
   * Calls Question::parseAnswer and Question::setAnswer in the same method
   * @param answer string values received from Discord
   */
  default void parseAndSetAnswer(List<String> answer) {
    Optional<T> answerOpt = Optional.of( ExceptionUtils.uncheck(() -> this.parseAnswer(answer) ));
    this.setAnswer(answerOpt);
  }

}
