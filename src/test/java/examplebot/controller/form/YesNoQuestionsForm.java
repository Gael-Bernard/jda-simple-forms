package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.question.yesno.YesNoQuestion;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.FormMessageEditor;
import fr.gbernard.jdaforms.model.Question;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;
import java.util.Optional;

public class YesNoQuestionsForm {

  public static Form createForm() {

    final Question<?> optionalQuestion = YesNoQuestion.builder()
        .key("like-jdasf")
        .title("Do you like the JDA Simple Forms?")
        .subtitle("Don't worry, the dev will never know what you answered ;)")
        .build();

    final Question<?> question1 = YesNoQuestion.builder()
        .key("accept-fill-form")
        .title("Do you want to continue?")
        .optionalNextQuestion(form -> form.findAnswerByKey("accept-fill-form", Boolean.class) ?
            Optional.of( optionalQuestion ) : Optional.empty() )
        .build();

    final Question<?> question2 = YesNoQuestion.builder()
        .key("healthy_food")
        .title("Do you eat healthily? :medical_symbol:")
        .build();

    final FormMessageEditor finalMessage = (hookToMessage, form) -> {

      if( form.findAnswerByKey("healthy_food", Boolean.class) ) {
        MessageEmbed messageEmbed = EmbedTemplate.basic("Congratulations!", "You're a healthy person.", EmbedColor.SUCCESS);
        EditMessage.embed(hookToMessage, messageEmbed);
      }
      else {
        MessageEmbed messageEmbed = EmbedTemplate.basic("Eat more soup!", "If you're lazy to eat healthy food, you can always have pumpkin soup. I swear it's delicious!", EmbedColor.WARNING);
        EditMessage.embed(hookToMessage, messageEmbed);
      }
    };

    return Form.builder()
        .questions(List.of(question1, question2))
        .ephemeral(true)
        .finalMessage(finalMessage)
        .build();
  }
}

