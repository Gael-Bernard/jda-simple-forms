package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.controller.messagedata.MessageGlobalParams;
import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
public class Form {

  public static long EMPTY_MESSAGE_ID_VALUE = -1;

  private @NonNull List<Question<?>> questions;
  private Question<?> currentQuestion;
  @Builder.Default
  private long messageId = EMPTY_MESSAGE_ID_VALUE;
  @Builder.Default
  private boolean ephemeral = MessageGlobalParams.DEFAULT_IS_EPHEMERAL;

  public boolean isMessageIdDefined() {
    return this.messageId != EMPTY_MESSAGE_ID_VALUE;
  }

}
