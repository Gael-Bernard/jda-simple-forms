package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.controller.template.MessageGlobalParams;
import lombok.*;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter @Setter
@Builder
@AllArgsConstructor
public class Form {

  public static long EMPTY_ID_VALUE = -1;

  public static BiConsumer<FormAnswersMap, Form> DEFAULT_ON_FORM_COMPLETE = (answersMap, form) -> {
    System.err.println("WARNING: onFormComplete was not implemented for this form. Current answers:");
    answersMap.keys().forEach(key -> System.err.println("- "+key+": "+answersMap.getAsObject(key).toString()));
  };

  private @NonNull List<Question<?>> questions;
  private Question<?> currentQuestion;
  @Builder.Default
  private long userId = EMPTY_ID_VALUE;
  @Builder.Default
  private long messageId = EMPTY_ID_VALUE;
  @Builder.Default
  private boolean ephemeral = MessageGlobalParams.DEFAULT_IS_EPHEMERAL;

  @Builder.Default
  private @NonNull BiConsumer<FormAnswersMap, Form> onFormComplete = DEFAULT_ON_FORM_COMPLETE;

}
