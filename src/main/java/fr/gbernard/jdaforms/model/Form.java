package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.exception.NoAnswerException;
import fr.gbernard.jdaforms.exception.NoCurrentQuestionException;
import fr.gbernard.jdaforms.exception.QuestionNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Function;

/**
 * Describes a form consisting of multiple questions
 */
@Accessors(chain = true)
@Getter @Setter
public class Form {

  public static long EMPTY_ID_VALUE = -1;

  /**
   * List of mandatory questions to ask user, defined by the developer
   */
  private @NonNull List<Question<?>> mandatoryQuestions;

  /**
   * List of questions already asked, including optional questions triggered by mandatory questions
   */
  private final @NonNull Stack<Question<?>> questionsHistory = new Stack<>();

  /**
   * States whether the form is complete or has remaining questions to answer
   */
  @Setter(AccessLevel.NONE)
  private boolean complete = false;

  /**
   * Unique ID of the user supposed to answer this question<br>
   * This value is set by the library.
   */
  private long userId = EMPTY_ID_VALUE;

  /**
   * Unique ID of the message being edited for every question<br>
   * This value is set and updated by the library.
   */
  private long messageId = EMPTY_ID_VALUE;

  /**
   * Whether the form should be invisible from the other users or not
   */
  private boolean ephemeral;

  /**
   * Action to perform once the form is complete
   */
  private @NonNull FormLastInteractionHandler onFormComplete;

  /**
   * Message to send when the form is cancelled
   */
  private @NonNull Function<Form, MessageCreateData> timeoutMessageSupplier;

  /**
   * Ensures the data structure of the mandatoryQuestion field is modifiable by transferring its values into an ArrayList
   */
  public void mapMandatoryQuestionsToModifiable() {
    this.mandatoryQuestions = new ArrayList<>(this.mandatoryQuestions);
  }

  /**
   * Current question waiting for a user answer if any
   */
  public Question<?> getCurrentQuestion() throws NoCurrentQuestionException {
    return getCurrentQuestionOptional()
        .orElseThrow(() -> new NoCurrentQuestionException("Form "+messageId+" doesn't have a current question"));
  }

  /**
   * Current question waiting for a user answer if any
   */
  public Optional<Question<?>> getCurrentQuestionOptional() {
    if(isComplete()) {
      return Optional.empty();
    }

    return Optional.of( questionsHistory.peek() );
  }

  /**
   * Marks the form as complete
   */
  public void setComplete() {
    this.complete = true;
  }

  /**
   * Finds a question by key
   * @param key key given at form creation
   * @param clazz expected type of the answer
   * @return matching question if any
   * @throws QuestionNotFoundException if no question matches key
   */
  public <T> Question<T> findQuestionByKey(String key, Class<T> clazz) throws QuestionNotFoundException {
    return this.mandatoryQuestions.stream()
        .filter(question -> question.getKey().equals(key))
        .map(value -> (Question<T>) value)
        .findAny()
        .orElseThrow(() -> new QuestionNotFoundException("Form doesn't contain question with key: "+key));
  }

  /**
   * Finds a question by key and retrieves its answer
   * @param key key given at form creation
   * @param clazz expected type of the answer
   * @return matching answer if any
   * @throws QuestionNotFoundException if no question matches key
   * @throws NoAnswerException if the question was found but doesn't have an answer yet
   */
  public <T> T findAnswerByKey(String key, Class<T> clazz) throws QuestionNotFoundException, NoAnswerException {
    return this.findQuestionByKey(key, clazz).getAnswer();
  }

}
