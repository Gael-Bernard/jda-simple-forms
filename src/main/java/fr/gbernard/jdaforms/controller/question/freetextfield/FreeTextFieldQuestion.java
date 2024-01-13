package fr.gbernard.jdaforms.controller.question.freetextfield;

import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.utils.messages.MessageEditData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@Accessors(chain = true)
@Getter @Setter
public class FreeTextFieldQuestion implements Question<String> {

  public static String MODAL_BUTTON_ID = "modal-open-button";
  public static String MODAL_ID = "free-text-field-question-modal";
  public static String TEXT_INPUT_ID = "free-text-field";

  private @NonNull QuestionSharedFields<String> sharedFields = new QuestionSharedFields<>();

  /**
   * Text to be displayed as description under the title of the original message
   * <br>This field is different from what will be displayed inside the pop-in modal window.
   */
  private @NonNull String subtitle;

  /**
   * Label of the button opening the pop-in modal window
   */
  private @NonNull String modalButtonLabel;

  /**
   * Title to display inside the pop-in modal window
   */
  private @Nullable String modalTitle;

  /**
   * Label to display above the input field inside the pop-in modal window
   */
  private @Nullable String fieldLabel;

  /**
   * Placeholder text to display inside the pop-in modal window's input field while the user hasn't typed anything
   */
  private @Nullable String fieldPlaceholder;

  /**
   * Type of input field
   * <br>This parameter mostly changes the input shape.
   */
  private @NonNull TextInputStyle textInputType;

  /**
   * Minimum length of user input text
   */
  private int inputMinLength;

  /**
   * Maximum length of user input text
   */
  private int inputMaxLength;

  public @NonNull String getModalTitle() {
    return Optional.ofNullable(modalTitle).orElse(getTitle());
  }

  public @NonNull String getFieldLabel() {
    return Optional.ofNullable(fieldLabel).orElse(getTitle());
  }

  /**
   * Function that edits the form message to display this question<br>
   */
  @Override
  public @NotNull FormMessageHookEditor getMessageEditor() {
    return (hookToMessage, form) -> {

      MessageEmbed embed = EmbedTemplate.basic(getTitle(), subtitle, EmbedColor.NEUTRAL);

      hookToMessage
          .editOriginal(MessageEditData.fromEmbeds(embed))
          .setActionRow(
              Button.primary(MODAL_BUTTON_ID, modalButtonLabel)
          ).queue();

    };
  }

  @Override
  public @NonNull FormInteractionOptionalModal getModalProviderInsteadOfHandler() {
    return (List<String> discordReturnedValues, Form form) -> {

      if(discordReturnedValues.get(0).equals(MODAL_BUTTON_ID)) {
        return Optional.of( generateModal() );
      }
      else {
        return Optional.empty();
      }
    };
  }

  @Override
  public @NotNull FormInteractionHandler getFormInteractionHandler() {
    return (discordReturnedValues, actions) -> actions.answerAndStartNextQuestion(discordReturnedValues.get(0));
  }

  private Modal generateModal() {

    final TextInput textArea = TextInput.create(TEXT_INPUT_ID, getFieldLabel(), textInputType)
        .setRequired(true)
        .setPlaceholder(fieldPlaceholder)
        .setRequiredRange(inputMinLength, inputMaxLength)
        .build();

    return Modal.create(MODAL_ID, getModalTitle())
        .addComponents(ActionRow.of(textArea))
        .build();
  }


}
