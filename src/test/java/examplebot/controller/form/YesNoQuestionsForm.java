package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.question.yesno.YesNoQuestion;
import fr.gbernard.jdaforms.model.Form;

import java.util.List;

public class YesNoQuestionsForm {

  public static Form createForm() {
    return Form.builder()
        .questions(List.of(

            YesNoQuestion.builder()
                .key("accept-fill-form")
                .title("Do you want to continue?")
                .build(),

            YesNoQuestion.builder()
                .key("like-jdasf")
                .title("Do you like the JDA Simple Forms?")
                .subtitle("Don't worry, the dev will never know what you answered ;)")
                .build(),

            YesNoQuestion.builder()
                .key("healthy_food")
                .title("Do you eat healthily? :medical_symbol:")
                .build()

        )).ephemeral(true).build();
  }
}

