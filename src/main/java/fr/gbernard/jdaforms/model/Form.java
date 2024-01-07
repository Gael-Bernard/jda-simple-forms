package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessagesEditors;
import fr.gbernard.jdaforms.controller.defaultmessages.DefaultSummary;
import fr.gbernard.jdaforms.controller.template.MessageGlobalParams;
import fr.gbernard.jdaforms.exception.NoAnswerException;
import fr.gbernard.jdaforms.exception.QuestionNotFoundException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiConsumer;

/**
 * Describes a form consisting of multiple questions
 */
@Getter @Setter
@Builder
@AllArgsConstructor
public class Form {

  public static long EMPTY_ID_VALUE = -1;

  public static BiConsumer<FormAnswersMap, Form> DEFAULT_ON_FORM_COMPLETE = (answersMap, form) -> {
    System.err.println("WARNING: onFormComplete was not implemented for this form. Current answers:");
    answersMap.keys().forEach(key -> System.err.println("- "+key+": "+answersMap.getAsObject(key).toString()));
  };

  /**
   * List of mandatory questions to ask user, defined by the developer
   */
  private @NonNull List<Question<?>> mandatoryQuestions;

  /**
   * List of questions already asked, including optional questions triggered by mandatory questions
   */
  @Setter(AccessLevel.NONE)
  @Builder.Default
  private @NonNull Stack<Question<?>> questionsHistory = new Stack<>();

  /**
   * States whether the form is complete or has remaining questions to answer
   */
  @Setter(AccessLevel.NONE)
  @Builder.Default
  private boolean complete = false;

  /**
   * Unique ID of the user supposed to answer this question<br>
   * This value is set by the library.
   */
  @Builder.Default
  private long userId = EMPTY_ID_VALUE;

  /**
   * Unique ID of the message being edited for every question<br>
   * This value is set and updated by the library.
   */
  @Builder.Default
  private long messageId = EMPTY_ID_VALUE;

  /**
   * Whether the form should be invisible from the other users or not
   */
  @Builder.Default
  private boolean ephemeral = MessageGlobalParams.DEFAULT_IS_EPHEMERAL;

  /**
   * Maps an almost-complete form into a text summary of the answers
   */
  @Builder.Default
  private SummaryTextProvider answersSummarySupplier = DefaultSummary::buildList;

  /**
   * Edits the form message when the form is complete
   */
  @Builder.Default
  private @NonNull FormMessageHookEditor finalMessage = DefaultMessagesEditors.formSent();

  /**
   * Action to perform once the form is complete
   */
  @Builder.Default
  private @NonNull BiConsumer<FormAnswersMap, Form> onFormComplete = DEFAULT_ON_FORM_COMPLETE;

  /**
   * Ensures the data structure of the mandatoryQuestion field is modifiable
   */
  public void mapMandatoryQuestionsToModifiable() {
    this.mandatoryQuestions = new ArrayList<>(this.mandatoryQuestions);
  }

  /**
   * Current question waiting for a user answer
   */
  public Optional<Question<?>> getCurrentQuestion() {
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
    return this.findQuestionByKey(key, clazz)
        .getAnswer()
        .orElseThrow(() -> new NoAnswerException("Question with key "+key+" doesn't have an answer"));
  }

}
