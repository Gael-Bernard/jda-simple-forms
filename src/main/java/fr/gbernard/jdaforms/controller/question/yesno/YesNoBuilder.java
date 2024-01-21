package fr.gbernard.jdaforms.controller.question.yesno;

import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessageCreateDatas;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Builder for YesNoQuestion
 */
@Accessors(chain = true, fluent = true)
@Getter @Setter
public class YesNoBuilder {

  public static BiPredicate<Boolean, Form> INPUT_VALIDATOR = (s, form) -> true;
  public static Function<Boolean, MessageCreateData> VALIDATION_ERROR_MESSAGE = DefaultMessageCreateDatas.objectValidationError();
  public static String DEFAULT_SUBTITLE = "";
  public static String DEFAULT_YES_LABEL = "YES";
  public static String DEFAULT_NO_LABEL = "NO";

  /* -----------------------------------------
    Shared between questions
  ----------------------------------------- */

  /**
   * Unique key used to find the question and its answer in the form
   */
  private @NotNull String key = Question.DEFAULT_KEY;

  /**
   * Question title, often but not necessarily displayed by questions
   */
  private @NotNull String title = Question.DEFAULT_TITLE;

  /**
   * Title to display in form summary before user validates
   * <br>Defaults to value of title field if not defined
   */
  private @Nullable String summaryTitle;

  /**
   * Provider of the current question
   */
  private @NotNull Function<Form, Optional<Question<?>>> optionalNextQuestion = Question.DEFAULT_OPTIONAL_NEXT_QUESTION;

  /* -----------------------------------------
    Specific to question type
  ----------------------------------------- */

  /**
   * Text to be displayed as description under the title
   */
  private @NotNull String subtitle = DEFAULT_SUBTITLE;

  /**
   * Label displayed on the "YES" answer button
   */
  private @NotNull String yesLabel = DEFAULT_YES_LABEL;

  /**
   * Label displayed on the "NO" answer button
   */
  private @NotNull String noLabel = DEFAULT_NO_LABEL;

  /* -----------------------------------------
    Mapper builder
  ----------------------------------------- */

  /**
   * Builds the ChannelDropdownQuestion
   */
  public YesNoQuestion build() {
    YesNoQuestion question = new YesNoQuestion();
    question.getSharedFields()
        .setKey(key)
        .setTitle(title)
        .setSummaryTitle(summaryTitle)
        .setInputValidator(INPUT_VALIDATOR)
        .setInvalidInputMessage(VALIDATION_ERROR_MESSAGE)
        .setOptionalNextQuestion(optionalNextQuestion);
    question
        .setSubtitle(subtitle)
        .setYesLabel(yesLabel)
        .setNoLabel(noLabel);
    return question;
  }

}
