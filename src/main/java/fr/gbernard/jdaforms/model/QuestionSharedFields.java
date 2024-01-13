package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.exception.NoAnswerException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

@Accessors(chain = true)
@Getter @Setter
public class QuestionSharedFields<T> {

  /**
   * Unique key used to find the question and its answer in the form
   */
  private @NonNull String key = Question.DEFAULT_KEY;

  /**
   * Question title, often but not necessarily displayed by questions
   */
  private @NonNull String title = Question.DEFAULT_TITLE;

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
   * States whether this question is complete and the library can move on to the next question
   */
  private boolean complete = Question.DEFAULT_IS_COMPLETE;

  /**
   * Provider of the current question
   */
  private @NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion = Question.DEFAULT_OPTIONAL_NEXT_QUESTION;

  /**
   * Function providing a modal to display instead of calling the regular form interaction handler
   */
  private @NonNull FormInteractionOptionalModal modalProviderInsteadOfHandler = Question.DEFAULT_OPTIONAL_MODAL;

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

}
