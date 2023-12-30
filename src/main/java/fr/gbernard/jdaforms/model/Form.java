package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.controller.template.MessageGlobalParams;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
   * List of questions to ask user, defined by the developer
   */
  private @NonNull List<Question<?>> questions;

  /**
   * Question currently waiting for an answer from the user
   */
  private Question<?> currentQuestion;



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
   * Action to perform once the form is complete
   */
  @Builder.Default
  private @NonNull BiConsumer<FormAnswersMap, Form> onFormComplete = DEFAULT_ON_FORM_COMPLETE;

  /**
   * Finds a question by key
   * @param key
   * @return matching question if any
   */
  public Optional<Question<?>> findQuestionByKey(String key) {
    return this.questions.stream()
        .filter(question -> question.getKey().equals(key))
        .findAny();
  }

}
