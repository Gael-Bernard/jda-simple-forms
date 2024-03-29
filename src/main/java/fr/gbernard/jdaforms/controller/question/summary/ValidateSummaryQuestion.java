package fr.gbernard.jdaforms.controller.question.summary;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessageCreateDatas;
import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessagesEditors;
import fr.gbernard.jdaforms.controller.defaultmessages.DefaultSummary;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.*;
import fr.gbernard.jdaforms.utils.ExceptionUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

@Accessors(chain = true)
@Getter @Setter
public class ValidateSummaryQuestion implements Question<Boolean> {

  public static BiPredicate<Boolean, Form> INPUT_VALIDATOR = (s, form) -> true;
  public static Function<Boolean, MessageCreateData> VALIDATION_ERROR_MESSAGE = DefaultMessageCreateDatas.objectValidationError();

  public static String SEND_BUTTON_ID = "send";
  public static String ALL_ANSWERS_BUTTON_ID = "answers";
  public static String CANCEL_BUTTON_ID = "cancel";

  public static String DEFAULT_KEY = "is-summary-ok-4ohxl0vawyqy";
  public static String DEFAULT_TITLE = "Are you done?";
  public static String DEFAULT_SUBTITLE = "You can check your answers one last time before we save them."
      +"\nIf you choose to cancel the form, your answers will be deleted.";
  public static String DEFAULT_SEND_LABEL = "Send my answers";
  public static String DEFAULT_ALL_ANSWERS_LABEL = "Check my answers";
  public static String DEFAULT_CANCEL_LABEL = "Cancel";

  private @NonNull QuestionSharedFields<Boolean> sharedFields = new QuestionSharedFields<>();
  {
    sharedFields
        .setKey(DEFAULT_KEY)
        .setTitle(DEFAULT_TITLE)
        .setInputValidator(INPUT_VALIDATOR)
        .setInvalidInputMessage(VALIDATION_ERROR_MESSAGE);
  }

  private @NonNull String subtitle = DEFAULT_SUBTITLE;
  private @NonNull String sendLabel = DEFAULT_SEND_LABEL;
  private @NonNull String allAnswersLabel = DEFAULT_ALL_ANSWERS_LABEL;
  private @NonNull String cancelLabel = DEFAULT_CANCEL_LABEL;
  private @NonNull FormMessageEditor cancelDoneMessage = DefaultMessagesEditors.formCancelled();

  /**
   * Maps an almost-complete form into a text summary of the answers
   */
  private @NotNull SummaryTextProvider answersSummary = DefaultSummary::buildList;


  public ValidateSummaryQuestion() { }

  public ValidateSummaryQuestion(SummaryTextProvider answersSummary) {
    this.answersSummary = answersSummary;
  }

  @Override
  public @NotNull FormMessageHookEditor getMessageEditor() {
    return (InteractionHook hookToMessage, Form form) -> {

      MessageEmbed embed = EmbedTemplate.basic(getTitle(), getSubtitle(), EmbedColor.NEUTRAL);
      List<ItemComponent> actionRows = List.of(
          Button.primary(SEND_BUTTON_ID, this.getSendLabel()),
          Button.secondary(ALL_ANSWERS_BUTTON_ID, getAllAnswersLabel()),
          Button.danger(CANCEL_BUTTON_ID, this.getCancelLabel())
      );
      EditMessage.embedAndItemComponents(hookToMessage, embed, actionRows);
    };
  }

  @Override
  public @NotNull FormInteractionHandler getFormInteractionHandler() {
    return (discordReturnedValues, actions) -> {

      final String buttonId = discordReturnedValues.get(0);

      if(buttonId.equals(SEND_BUTTON_ID)) {
        actions.answerAndStartNextQuestion(true);
      }
      else if(buttonId.equals(CANCEL_BUTTON_ID)) {
        actions.cancelForm();
        ExceptionUtils.uncheck(() -> cancelDoneMessage.edit(actions.getMessage(), actions.getForm()) );
      }
      else if(buttonId.equals(ALL_ANSWERS_BUTTON_ID)) {
        final String allAnswers = answersSummary.apply(actions.getForm());
        actions.getMessage().reply(allAnswers)
            .setEphemeral(true)
            .queue();
      }
    };
  }
}
