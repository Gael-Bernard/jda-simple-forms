package fr.gbernard.jdaforms.controller.question.freetextfield;

import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.*;
import lombok.*;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.utils.messages.MessageEditData;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Getter @Setter
@Builder
@AllArgsConstructor
public class FreeTextFieldQuestion implements Question<String> {

  public static String MODAL_BUTTON_ID = "modal-open-button";
  public static String MODAL_ID = "free-text-field-question-modal";
  public static String TEXT_INPUT_ID = "free-text-field";
  public static String DEFAULT_SUBTITLE = "You may answer either by pressing the button below or by typing ``/answ [your-answer]``.";
  public static String DEFAULT_MODAL_BUTTON_LABEL = "Answer in the pop-in";

  private @NonNull String key;
  private String summaryTitle;

  private @NonNull String modalTitle;
  private @NonNull String fieldTitle;
  private String fieldPlaceholder;
  @Builder.Default
  private @NonNull TextInputStyle textInputType = TextInputStyle.SHORT;
  @Builder.Default
  private int inputMinLength = 1;
  @Builder.Default
  private int inputMaxLength = 128;

  private String instructionsTitle;
  @Builder.Default
  private @NonNull String instructionsSubtitle = DEFAULT_SUBTITLE;
  @Builder.Default
  private @NonNull String modalButtonLabel = DEFAULT_MODAL_BUTTON_LABEL;

  @Builder.Default
  private @NonNull Optional<String> answer = Optional.empty();
  @Builder.Default
  private boolean complete = false;
  @Builder.Default
  private @NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion = form -> Optional.empty();

  public @NonNull String getSummaryTitle() {
    return Optional.ofNullable(summaryTitle).orElse(modalTitle);
  }

  public @NonNull String getInstructionsTitle() {
    return Optional.ofNullable(instructionsTitle).orElse(modalTitle);
  }

  /**
   * Function that edits the form message to display this question<br>
   */
  @Override
  public FormMessageHookEditor getMessageEditor() {
    return (hookToMessage, form) -> {

      MessageEmbed embed = EmbedTemplate.basic(getInstructionsTitle(), instructionsSubtitle, EmbedColor.NEUTRAL);

      hookToMessage
          .editOriginal(MessageEditData.fromEmbeds(embed))
          .setActionRow(
              Button.primary(MODAL_BUTTON_ID, modalButtonLabel)
          ).queue();

    };
  }

  @Override
  public FormInteractionOptionalModal getModalProviderInsteadOfHandler() {
    return (List<String> discordReturnedValues, Form form) -> {

      if(discordReturnedValues.get(0).equals(MODAL_BUTTON_ID)) {
        return Optional.of( generateModal() );
      }
      else {
        return Optional.empty();
      }
    };
  }

  /**
   * Function that saves the received answer and optionally performs other response actions
   */
  @Override
  public FormInteractionHandler getFormInteractionHandler() {
    return (discordReturnedValues, actions) -> actions.answerAndStartNextQuestion(discordReturnedValues.get(0));
  }

  private Modal generateModal() {

    final TextInput textArea = TextInput.create(TEXT_INPUT_ID, fieldTitle, textInputType)
        .setRequired(true)
        .setPlaceholder(fieldPlaceholder)
        .setRequiredRange(inputMinLength, inputMaxLength)
        .build();

    return Modal.create(MODAL_ID, modalTitle)
        .addComponents(ActionRow.of(textArea))
        .build();
  }


}
