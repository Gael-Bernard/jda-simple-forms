package fr.gbernard.jdaforms.controller.question;

import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessageCreateDatas;
import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessagesEditors;
import fr.gbernard.jdaforms.controller.template.MessageGlobalParams;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.FormLastInteractionHandler;
import fr.gbernard.jdaforms.model.Question;
import fr.gbernard.jdaforms.utils.ExceptionUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

@Accessors(fluent = true, chain = true)
@Getter @Setter
public class FormBuilder {

  public static FormLastInteractionHandler DEFAULT_ON_FORM_COMPLETE = (hook, answersMap, form) -> {
    System.err.println("WARNING: onFormComplete was not implemented for this form. Current answers:");
    answersMap.keys().forEach(key -> System.err.println("- "+key+": "+answersMap.getAsObject(key).toString()));
    ExceptionUtils.uncheck(() -> DefaultMessagesEditors.formSent().edit(hook, form) );
  };

  /**
   * List of mandatory questions to ask user
   */
  private @NotNull List<Question<?>> questions;

  /**
   * Whether the form should be invisible from the other users or not
   */
  private boolean ephemeral = MessageGlobalParams.DEFAULT_IS_EPHEMERAL;

  /**
   * Action to perform once the form is complete
   */
  private @NotNull FormLastInteractionHandler onFormComplete = DEFAULT_ON_FORM_COMPLETE;

  /**
   * Message to send when the form is cancelled
   */
  private @NonNull Function<Form, MessageCreateData> timeoutMessage = DefaultMessageCreateDatas.timeoutMessage();

  /**
   * Builds the Form instance
   */
  public Form build() {
    return new Form()
        .setMandatoryQuestions(questions)
        .setEphemeral(ephemeral)
        .setOnFormComplete(onFormComplete)
        .setTimeoutMessageSupplier(timeoutMessage);
  }

}
