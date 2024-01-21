package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.exception.NoAnswerException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * POJO containing all the fields shared between Question implementations
 * @param <T> expected type of the question's answer
 */
@Accessors(chain = true)
@Getter @Setter
public class QuestionSharedFields<T> {

  /**
   * Unique key used to find the question and its answer in the form
   */
  private @NonNull String key;

  /**
   * Question title, often but not necessarily displayed by questions
   */
  private @NonNull String title;

  /**
   * Title to display in form summary before user validates
   * <br>Defaults to value of title field if not defined
   */
  private @Nullable String summaryTitle;

  /**
   * Parsed answer given by the user if any
   */
  private @Nullable T answer;

  /**
   * User input validator
   * <br>If the input string is invalid (returning false), the question is asked again.
   * <br>The validator can throw RuntimeException safely, interpreted as invalid input.
   */
  private @NonNull BiPredicate<T, Form> inputValidator;

  /**
   * Message the user receives after inputting an invalid value
   */
  private @NonNull Function<T, MessageCreateData> invalidInputMessage;

  /**
   * States whether this question is complete and the library can move on to the next question
   */
  private boolean complete = Question.DEFAULT_IS_COMPLETE;

  /**
   * Provider of the following question
   */
  private @Nullable Function<Form, Optional<Question<?>>> optionalNextQuestion;

  /**
   * Function providing a modal to display instead of calling the regular form interaction handler
   */
  private @Nullable FormInteractionOptionalModal modalProviderInsteadOfHandler;

  /**
   * Title to display in form summary before user validates
   * <br>Default to value of title field
   */
  public @NonNull String getSummaryTitle() {
    return Optional.ofNullable(summaryTitle).orElse(title);
  }

  /**
   * Parsed answer given by the user if any
   * @throws NoAnswerException if the question was absent
   */
  public @NonNull T getAnswer() throws NoAnswerException {
    if (answer == null) {
      throw new NoAnswerException("Question "+key+" has no answer yet");
    }
    return answer;
  }

  /**
   * Parsed answer given by the user if any
   */
  public Optional<T> getAnswerOptional() {
    return Optional.ofNullable(answer);
  }

  /**
   * Provider of the following question
   */
  public @NonNull Function<Form, Optional<Question<?>>> getOptionalNextQuestion() {
    return Optional.ofNullable(optionalNextQuestion).orElse(Question.DEFAULT_OPTIONAL_NEXT_QUESTION);
  }

  /**
   * Function providing a modal to display instead of calling the regular form interaction handler
   */
  public @NonNull FormInteractionOptionalModal getModalProviderInsteadOfHandler() {
    return Optional.ofNullable(modalProviderInsteadOfHandler).orElse(Question.DEFAULT_OPTIONAL_MODAL);
  }

}
