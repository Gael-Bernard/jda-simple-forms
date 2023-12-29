package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.controller.template.MessageGlobalParams;
import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
public class Form {

  public static long EMPTY_ID_VALUE = -1;

  private @NonNull List<Question<?>> questions;
  private Question<?> currentQuestion;
  @Builder.Default
  private long userId = EMPTY_ID_VALUE;
  @Builder.Default
  private long messageId = EMPTY_ID_VALUE;
  @Builder.Default
  private boolean ephemeral = MessageGlobalParams.DEFAULT_IS_EPHEMERAL;

}
