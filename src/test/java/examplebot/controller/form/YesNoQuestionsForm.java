package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.question.yesno.YesNoQuestion;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.*;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class YesNoQuestionsForm {

  public static Form createForm() {

    final Question<?> optionalQuestion = new YesNoQuestion()
        .setSubtitle("Don't worry, the dev will never know what you answered ;)");
    optionalQuestion.getSharedFields()
        .setKey("like-jdasf")
        .setSummaryTitle("Like JDA Simple Forms?")
        .setTitle("Do you like the JDA Simple Forms?");

    final Question<?> question1 = new YesNoQuestion();
    question1.getSharedFields()
        .setKey("accept-fill-form")
        .setSummaryTitle("Continue?")
        .setTitle("Do you want to continue?")
        .setOptionalNextQuestion(form -> form.findAnswerByKey("accept-fill-form", Boolean.class) ?
            Optional.of( optionalQuestion ) : Optional.empty() );

    final Question<?> question2 = new YesNoQuestion();
    question2.getSharedFields()
        .setKey("healthy_food")
        .setSummaryTitle("Eating healthy food?")
        .setTitle("Do you eat healthily? :medical_symbol:");

    final FormMessageHookEditor finalMessage = (hookToMessage, form) -> {

      if( form.findAnswerByKey("healthy_food", Boolean.class) ) {
        MessageEmbed messageEmbed = EmbedTemplate.basic("Congratulations!", "You're a healthy person.", EmbedColor.SUCCESS);
        EditMessage.embed(hookToMessage, messageEmbed);
      }
      else {
        MessageEmbed messageEmbed = EmbedTemplate.basic("Eat more soup!", "If you're lazy to eat healthy food, you can always have pumpkin soup. I swear it's delicious!", EmbedColor.WARNING);
        EditMessage.embed(hookToMessage, messageEmbed);
      }
    };

    final SummaryTextProvider summaryProvider = form ->
      "**Your great answers**: "
          +form.getQuestionsHistory().stream()
          .map(question -> "["+question.getSummaryTitle()+" | "+question.getAnswerOptional().orElse(null)+"]")
          .collect(Collectors.joining());

    return Form.builder()
        .mandatoryQuestions(List.of(question1, question2))
        .ephemeral(true)
        .answersSummarySupplier(summaryProvider)
        .finalMessage(finalMessage)
        .build();
  }
}

