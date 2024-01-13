package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.question.yesno.YesNoQuestion;
import fr.gbernard.jdaforms.model.Form;

import java.util.List;

public class SingleYesNoQuestionForm {

  public static Form createForm() {

    final YesNoQuestion question = new YesNoQuestion()
        .setYesLabel("SHUT UP AND TAKE MY MONEY!")
        .setNoLabel("Not interested, sorry");
    question.getSharedFields()
        .setKey("single-yes-no-question")
        .setTitle("Please press \"SHUT UP AND TAKE MY MONEY!\" now");


    return Form.builder()
        .mandatoryQuestions(List.of(question))
        .onFormComplete((answers, completeForm) -> {
          final boolean answer = answers.getAsBoolean("single-yes-no-question");
          System.out.println("User has selected button: "+answer);
        })
        .build();
  }

}
