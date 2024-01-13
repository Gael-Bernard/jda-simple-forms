package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.exception.NoAnswerException;
import lombok.NonNull;

import java.util.Optional;
import java.util.function.Function;

/**
 * Question to ask the user
 * @param <T> type of the answer
 */
public interface Question<T> {

  String DEFAULT_KEY = "default-question-key";
  String DEFAULT_TITLE = "Untitled at question creation";
  boolean DEFAULT_IS_COMPLETE = false;
  Function<Form, Optional<Question<?>>> DEFAULT_OPTIONAL_NEXT_QUESTION = f -> Optional.empty();
  FormInteractionOptionalModal DEFAULT_OPTIONAL_MODAL = (vals, f) -> Optional.empty();

  /**
   * POJO containing the fields common to all questions
   * <br>These are mostly the necessary fields for forms to be proceeded.
   */
  @NonNull QuestionSharedFields<T> getSharedFields();

  /**
   * Function that edits the form message to display this question<br>
   */
  @NonNull FormMessageHookEditor getMessageEditor();

  /**
   * Function that saves the received answer and optionally performs other response actions
   */
  @NonNull FormInteractionHandler getFormInteractionHandler();

  /**
   * Unique key used to find the question and its answer in the form
   */
  default @NonNull String getKey() {
    return this.getSharedFields().getKey();
  }

  /**
   * Question title, often but not necessarily displayed by questions
   */
  default @NonNull String getTitle() {
    return this.getSharedFields().getTitle();
  }

  /**
   * Title to display in form summary before user validates
   * <br>Defaults to value of title field if not defined
   */
  default @NonNull String getSummaryTitle() {
    return this.getSharedFields().getSummaryTitle();
  }

  /**
   * Parsed answer given by the user if any
   * @throws NoAnswerException if the question was absent
   */
  default @NonNull T getAnswer() throws NoAnswerException {
    return this.getSharedFields().getAnswer();
  }

  /**
   * Parsed answer given by the user if any
   */
  default Optional<T> getAnswerOptional() {
    return this.getSharedFields().getAnswerOptional();
  }

  /**
   * States whether this question is complete and the library can move on to the next question
   */
  default boolean isComplete() {
    return this.getSharedFields().isComplete();
  }

  /**
   * Provider of the current question
   */
  default @NonNull Function<Form, Optional<Question<?>>> getOptionalNextQuestion() {
    return this.getSharedFields().getOptionalNextQuestion();
  }

  /**
   * Function providing a modal to display instead of calling the regular form interaction handler
   */
  default @NonNull FormInteractionOptionalModal getModalProviderInsteadOfHandler() {
    return this.getSharedFields().getModalProviderInsteadOfHandler();
  }

}
