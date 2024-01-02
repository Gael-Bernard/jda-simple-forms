package fr.gbernard.jdaforms.controller.question.summary;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessagesEditors;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.FormInteractionHandler;
import fr.gbernard.jdaforms.model.FormMessageEditor;
import fr.gbernard.jdaforms.model.Question;
import fr.gbernard.jdaforms.utils.ExceptionUtils;
import lombok.*;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Getter @Setter
@Builder
@AllArgsConstructor
public class ValidateSummaryQuestion implements Question<Boolean> {

  public static String SEND_BUTTON_ID = "send";
  public static String ALL_ANSWERS_BUTTON_ID = "answers";
  public static String CANCEL_BUTTON_ID = "cancel";

  public static String DEFAULT_TITLE = "Are you done?";
  public static String DEFAULT_SUBTITLE = "You can check your answers one last time before we save them."
      +"\nIf you choose to cancel the form, your answers will be deleted.";
  public static String DEFAULT_SEND_LABEL = "Send my answers";
  public static String DEFAULT_ALL_ANSWERS_LABEL = "Check my answers";
  public static String DEFAULT_CANCEL_LABEL = "Cancel";

  private @NonNull String key;
  private String summaryTitle;
  @Builder.Default
  private @NonNull String title = DEFAULT_TITLE;
  @Builder.Default
  private @NonNull String subtitle = DEFAULT_SUBTITLE;
  @Builder.Default
  private String sendLabel = DEFAULT_SEND_LABEL;
  @Builder.Default
  private String allAnswersLabel = DEFAULT_ALL_ANSWERS_LABEL;
  @Builder.Default
  private String cancelLabel = DEFAULT_CANCEL_LABEL;
  @Builder.Default
  private @NonNull FormMessageEditor cancelDoneMessage = DefaultMessagesEditors.formCancelled();

  @Builder.Default
  private @NonNull Optional<Boolean> answer = Optional.empty();
  @Builder.Default
  private boolean complete = false;
  @Builder.Default
  private @NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion = form -> Optional.empty();
  
  public @NonNull String getSummaryTitle() {
    return Optional.ofNullable(summaryTitle).orElse(title);
  }

  @Override
  public FormMessageEditor getMessageEditor() {
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
  public FormInteractionHandler getFormInteractionHandler() {
    return (discordReturnedValues, actions) -> {

      final String buttonId = discordReturnedValues.get(0);

      if(buttonId.equals(SEND_BUTTON_ID)) {
        actions.answerAndStartNextQuestion(true);
      }
      else if(buttonId.equals(CANCEL_BUTTON_ID)) {
        actions.cancelForm();
        ExceptionUtils.uncheck(() -> cancelDoneMessage.edit(actions.getHook(), actions.getForm()) );
      }
      else if(buttonId.equals(ALL_ANSWERS_BUTTON_ID)) {
        final String allAnswers = actions.getForm().getAnswersSummarySupplier().apply(actions.getForm());
        actions.getHook().sendMessage(allAnswers)
            .setEphemeral(true)
            .queue();
      }
    };
  }
}
