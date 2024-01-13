package fr.gbernard.jdaforms.controller.question.dropdown;

import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.Question;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

/**
 * Builder for RoleDropdownQuestion
 */
@Accessors(chain = true, fluent = true)
@Getter @Setter
public class RoleDropdownBuilder {

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
  public RoleDropdownQuestion build() {
    RoleDropdownQuestion question = new RoleDropdownQuestion();
    question.getSharedFields()
        .setKey(key)
        .setTitle(title)
        .setSummaryTitle(summaryTitle)
        .setOptionalNextQuestion(optionalNextQuestion);
    question
        .setSubtitle(subtitle)
        .setMinSelectedItems(minSelectedItems)
        .setMaxSelectedItems(maxSelectedItems);
    return question;
  }

}
