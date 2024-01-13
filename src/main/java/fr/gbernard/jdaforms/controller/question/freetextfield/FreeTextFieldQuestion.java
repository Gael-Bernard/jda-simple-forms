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

  public static String DEFAULT_SUBTITLE = "Click the button below to open the answering pop-in window.";
  public static String DEFAULT_MODAL_BUTTON_LABEL = "Answer in pop-in";
  public static TextInputStyle DEFAULT_TEXT_INPUT_TYPE = TextInputStyle.SHORT;
  public static int DEFAULT_INPUT_MIN_LENGTH = 1;
  public static int DEFAULT_INPUT_MAX_LENGTH = 128;

  public static String MODAL_BUTTON_ID = "modal-open-button";
  public static String MODAL_ID = "free-text-field-question-modal";
  public static String TEXT_INPUT_ID = "free-text-field";

  private @NonNull QuestionSharedFields<String> sharedFields = new QuestionSharedFields<>();

  private @NonNull String subtitle = DEFAULT_SUBTITLE;
  private @Nullable String modalTitle;
  private @Nullable String fieldLabel;
  private @Nullable String fieldPlaceholder;
  private @NonNull TextInputStyle textInputType = DEFAULT_TEXT_INPUT_TYPE;
  private int inputMinLength = DEFAULT_INPUT_MIN_LENGTH;
  private int inputMaxLength = DEFAULT_INPUT_MAX_LENGTH;
  private @NonNull String modalButtonLabel = DEFAULT_MODAL_BUTTON_LABEL;

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
