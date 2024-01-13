package fr.gbernard.jdaforms.controller.question.freetextfield;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

/**
 * Builder for FreeTextFieldQuestion
 */
@Accessors(chain = true, fluent = true)
@Getter @Setter
public class FreeTextFieldBuilder {

  public static String DEFAULT_SUBTITLE = "Click the button below to open the answering pop-in window.";
  public static String DEFAULT_MODAL_BUTTON_LABEL = "Answer in pop-in";
  public static TextInputStyle DEFAULT_TEXT_INPUT_TYPE = TextInputStyle.SHORT;
  public static int DEFAULT_INPUT_MIN_LENGTH = 1;
  public static int DEFAULT_INPUT_MAX_LENGTH = 128;

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
   * Text to be displayed as description under the title of the original message
   * <br>This field is different from what will be displayed inside the pop-in modal window.
   */
  private @NotNull String subtitle = DEFAULT_SUBTITLE;

  /**
   * Label of the button opening the pop-in modal window
   */
  private @NotNull String modalButtonLabel = DEFAULT_MODAL_BUTTON_LABEL;

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
  private @NotNull TextInputStyle textInputType = DEFAULT_TEXT_INPUT_TYPE;

  /**
   * Minimum length of user input text
   */
  private int inputMinLength = DEFAULT_INPUT_MIN_LENGTH;

  /**
   * Maximum length of user input text
   */
  private int inputMaxLength = DEFAULT_INPUT_MAX_LENGTH;

  /* -----------------------------------------
    Mapper builder
  ----------------------------------------- */

  /**
   * Builds the ChannelDropdownQuestion
   */
  public FreeTextFieldQuestion build() {
    FreeTextFieldQuestion question = new FreeTextFieldQuestion();
    question.getSharedFields()
        .setKey(key)
        .setTitle(title)
        .setSummaryTitle(summaryTitle)
        .setOptionalNextQuestion(optionalNextQuestion);
    question
        .setSubtitle(subtitle)
        .setModalTitle(modalTitle)
        .setFieldLabel(fieldLabel)
        .setFieldPlaceholder(fieldPlaceholder)
        .setTextInputType(textInputType)
        .setInputMinLength(inputMinLength)
        .setInputMaxLength(inputMaxLength)
        .setModalButtonLabel(modalButtonLabel);
    return question;
  }

}
