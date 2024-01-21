package fr.gbernard.jdaforms.controller.question.dropdown;

import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessageCreateDatas;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Builder for UserDropdownQuestion
 */
@Accessors(chain = true, fluent = true)
@Getter @Setter
public class UserDropdownBuilder {

  public static BiPredicate<List<User>, Form> DEFAULT_INPUT_VALIDATOR = (s, form) -> true;
  public static Function<List<User>, MessageCreateData> DEFAULT_VALIDATION_MESSAGE = DefaultMessageCreateDatas.objectValidationError();

  public static int DEFAULT_MIN_SELECTED = 1;
  public static int DEFAULT_MAX_SELECTED = 1;

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
   * User input validator
   * <br>If the input string is invalid (returning false), the question is asked again.
   * <br>The validator can throw RuntimeException safely, interpreted as invalid input.
   */
  private @NonNull BiPredicate<List<User>, Form> inputValidator = DEFAULT_INPUT_VALIDATOR;

  /**
   * Message the user receives after inputting an invalid value
   */
  private @NonNull Function<List<User>, MessageCreateData> invalidInputMessage = DEFAULT_VALIDATION_MESSAGE;

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
  private @Nullable String subtitle;

  /**
   * Minimum number of items user should pick from the list
   */
  private int minSelectedItems = DEFAULT_MIN_SELECTED;

  /**
   * Maximum number of items user should pick from the list
   */
  private int maxSelectedItems = DEFAULT_MAX_SELECTED;

  /* -----------------------------------------
    Mapper builder
  ----------------------------------------- */

  /**
   * Builds the ChannelDropdownQuestion
   */
  public UserDropdownQuestion build() {
    UserDropdownQuestion question = new UserDropdownQuestion();
    question.getSharedFields()
        .setKey(key)
        .setTitle(title)
        .setSummaryTitle(summaryTitle)
        .setInputValidator(inputValidator)
        .setInvalidInputMessage(invalidInputMessage)
        .setOptionalNextQuestion(optionalNextQuestion);
    question
        .setSubtitle(subtitle)
        .setMinSelectedItems(minSelectedItems)
        .setMaxSelectedItems(maxSelectedItems);
    return question;
  }

}
