package fr.gbernard.jdaforms.controller.question.dropdown;

import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessageCreateDatas;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Builder for CustomDropdownQuestion
 */
@Accessors(chain = true, fluent = true)
@Getter @Setter
public class CustomDropdownBuilder<T extends DropdownItem> {

  @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
  public BiPredicate<List<T>, Form> DEFAULT_INPUT_VALIDATOR = (s, form) -> true;
  @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
  public Function<List<T>, MessageCreateData> DEFAULT_VALIDATION_MESSAGE = DefaultMessageCreateDatas.objectValidationError();

  public static int DEFAULT_MIN_SELECTED = 1;
  public static int DEFAULT_MAX_SELECTED = 1;
  public static Function<String,?> DEFAULT_PARSER = s -> s;

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
  private @NonNull BiPredicate<List<T>, Form> inputValidator = DEFAULT_INPUT_VALIDATOR;

  /**
   * Message the user receives after inputting an invalid value
   */
  private @NonNull Function<List<T>, MessageCreateData> invalidInputMessage = DEFAULT_VALIDATION_MESSAGE;

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

  /**
   * Items of type T to propose in the dropdown
   */
  private @NotNull List<T> choices;

  /**
   * Parser from the value of an item to an instance of the item
   * <br>
   * <br>For example with Vegetable{label:"DELICIOUS POTATO", value: "potato"},
   * <br>the parser would convert as follows:
   * <br>> "potato" -> Vegetable{label:"DELICIOUS POTATO", value: "potato"}
   */
  private @NotNull Function<String,T> parser = (Function<String,T>) DEFAULT_PARSER;

  /* -----------------------------------------
    Mapper builder
  ----------------------------------------- */

  /**
   * Builds the ChannelDropdownQuestion
   */
  public CustomDropdownQuestion<T> build() {
    CustomDropdownQuestion<T> question = new CustomDropdownQuestion<>();
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
        .setMaxSelectedItems(maxSelectedItems)
        .setChoices(choices)
        .setParser(parser);
    return question;
  }

}
