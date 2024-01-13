package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.question.yesno.YesNoBuilder;
import fr.gbernard.jdaforms.controller.question.yesno.YesNoQuestion;
import fr.gbernard.jdaforms.model.Form;

import java.util.List;

public class SingleYesNoQuestionForm {

  public static Form createForm() {

    final YesNoQuestion question = new YesNoBuilder()
        .key("single-yes-no-question")
        .title("Please press \"SHUT UP AND TAKE MY MONEY!\" now")
        .yesLabel("SHUT UP AND TAKE MY MONEY!")
        .noLabel("Not interested, sorry")
        .build();

    return Form.builder()
        .mandatoryQuestions(List.of(question))
        .onFormComplete((answers, completeForm) -> {
          final boolean answer = answers.getAsBoolean("single-yes-no-question");
          System.out.println("User has selected button: "+answer);
        })
        .build();
  }

}
