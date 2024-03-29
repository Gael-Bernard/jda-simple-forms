package examplebot.controller.form;

import fr.gbernard.jdaforms.controller.action.EditMessage;
import fr.gbernard.jdaforms.controller.question.FormBuilder;
import fr.gbernard.jdaforms.controller.question.summary.ValidateSummaryQuestion;
import fr.gbernard.jdaforms.controller.question.yesno.YesNoBuilder;
import fr.gbernard.jdaforms.controller.template.EmbedColor;
import fr.gbernard.jdaforms.controller.template.EmbedTemplate;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.FormMessageHookEditor;
import fr.gbernard.jdaforms.model.Question;
import fr.gbernard.jdaforms.model.SummaryTextProvider;
import fr.gbernard.jdaforms.utils.ExceptionUtils;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class YesNoQuestionsForm {

  public static Form createForm() {

    final Question<?> optionalQuestion = new YesNoBuilder()
        .key("like-jdasf")
        .summaryTitle("Like JDA Simple Forms?")
        .title("Do you like the JDA Simple Forms?")
        .subtitle("Don't worry, the dev will never know what you answered ;)")
        .build();

    final Question<?> question1 = new YesNoBuilder()
        .key("accept-fill-form")
        .summaryTitle("Continue?")
        .title("Do you want to continue?")
        .optionalNextQuestion(form -> form.findAnswerByKey("accept-fill-form", Boolean.class) ?
            Optional.of( optionalQuestion ) : Optional.empty() )
        .build();

    final Question<?> question2 = new YesNoBuilder()
        .key("healthy_food")
        .summaryTitle("Eating healthy food?")
        .title("Do you eat healthily? :medical_symbol:")
        .build();


    final SummaryTextProvider summaryProvider = form ->
        "**Your great answers**: "
            +form.getQuestionsHistory().stream()
            .map(question -> "["+question.getSummaryTitle()+" | "+question.getAnswerOptional().orElse(null)+"]")
            .collect(Collectors.joining());

    final ValidateSummaryQuestion validateQuestion = new ValidateSummaryQuestion(summaryProvider);

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

    return new FormBuilder()
        .questions(List.of(question1, question2, validateQuestion))
        .ephemeral(true)
        .onFormComplete((hookToMessage, answersMap, form) ->
            ExceptionUtils.uncheck(() -> finalMessage.edit(hookToMessage, form))
        )
        .build();
  }
}

