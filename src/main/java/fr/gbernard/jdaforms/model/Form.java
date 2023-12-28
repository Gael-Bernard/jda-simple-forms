package fr.gbernard.jdaforms.model;

import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
public class Form {

  public static long EMPTY_MESSAGE_ID_VALUE = -1;

  @Builder.Default
  private long messageId = EMPTY_MESSAGE_ID_VALUE;
  private @NonNull List<Question> questions;
  @Builder.Default
  private boolean ephemeral = false;

  public boolean isMessageIdDefined() {
    return this.messageId != EMPTY_MESSAGE_ID_VALUE;
  }

}
