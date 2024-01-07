package fr.gbernard.jdaforms.model;

import lombok.NonNull;

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
   * Title to display in form summary before user validates
   */
  @NonNull String getSummaryTitle();
  /**
   * Title to display in form summary before user validates
   */
  void setSummaryTitle(String summaryTitle);

  /**
   * Parsed answer given by the user if any
   */
  @NonNull Optional<T> getAnswer();
  /**
   * Parsed answer given by the user if any
   */
  void setAnswer(@NonNull Optional<T> answer);

  /**
   * States whether this quesiton is complete and the library can move on to the next question
   */
  boolean isComplete();
  /**
   * States whether this quesiton is complete and the library can move on to the next question
   */
  void setComplete(boolean isComplete);

  /**
   * Provider of the current question
   */
  @NonNull Function<Form, Optional<Question<?>>> getOptionalNextQuestion();
  void setOptionalNextQuestion(@NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion);

  /**
   * Function that edits the form message to display this question<br>
   */
  FormMessageHookEditor getMessageEditor();

  /**
   * Function providing a modal to display instead of calling the regular handler
   */
  FormInteractionOptionalModal getModalProviderInsteadOfHandler();

  /**
   * Function that saves the received answer and optionally performs other response actions
   */
  FormInteractionHandler getFormInteractionHandler();

}
