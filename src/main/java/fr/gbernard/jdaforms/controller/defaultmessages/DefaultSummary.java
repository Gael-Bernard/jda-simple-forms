package fr.gbernard.jdaforms.controller.defaultmessages;

import fr.gbernard.jdaforms.model.Form;

import java.util.Optional;

public class DefaultSummary {

  public static String DEFAULT_TITLE = "**YOUR REPLIES** ⤵️";
  public static String UNDEFINED_ANSWER_PLACEHOLDER = "*Non-answered*";

  public static String buildList(Form form) {

    StringBuffer builder = new StringBuffer()
        .append(DEFAULT_TITLE)
        .append(System.lineSeparator())
        .append(System.lineSeparator());

    form.getQuestionsHistory().forEach(question -> {
      Optional<?> answerOpt = question.getAnswer();
      builder
          .append("\uD83D\uDD18 **")
          .append(question.getSummaryTitle())
          .append("** -> ")
          .append(answerOpt.isPresent() ? "**"+answerOpt.get()+"**" : UNDEFINED_ANSWER_PLACEHOLDER)
          .append(System.lineSeparator());
    });

    return builder.toString();
  }

}
