package mocks.service;

import fr.gbernard.jdaforms.controller.question.yesno.YesNoQuestion;
import fr.gbernard.jdaforms.model.Question;

import java.util.Optional;

import static java.util.Optional.of;

public class QuestionMocks {

  public static Question<Boolean> baseYesNoQuestion1() {
    final YesNoQuestion question = new YesNoQuestion()
        .setSubtitle("Have you ever heard of raclette? If not, you're missing something for sure!");
    question.getSharedFields()
        .setKey("is-raclette")
        .setTitle("Do you know raclette?");

    return question;
  }

  public static Question<Boolean> baseYesNoQuestion2() {
    final YesNoQuestion question = new YesNoQuestion()
        .setSubtitle("Please tell us if you prefer cats over dogs");
    question.getSharedFields()
        .setKey("cats")
        .setTitle("Do you prefer cats over dogs?");

    return question;
  }

  public static Question<Boolean> baseYesNoQuestion3() {
    final YesNoQuestion question = new YesNoQuestion()
        .setSubtitle("According to your knowledge, is the **Principality of Liechtenstein** the UN recognised country with the least land in Europe");
    question.getSharedFields()
        .setKey("liechtenstein-smallest-europe")
        .setTitle("Is Liechtenstein the smallest country in Europe?");

    return question;
  }

  public static Question<Boolean> baseYesNoQuestion4() {
    final YesNoQuestion question = new YesNoQuestion()
        .setSubtitle("Please tell us if you prefer camels over cats");
    question.getSharedFields()
        .setKey("camels")
        .setTitle("Do you prefer camels over cats?");

    return question;
  }

  public static Question<Boolean> simpleSubquestion1() {
    Question<Boolean> question = baseYesNoQuestion1();
    question.getSharedFields().setOptionalNextQuestion(form -> of(QuestionMocks.baseYesNoQuestion2()) );
    return question;
  }

  public static Question<Boolean> simpleSubquestion2() {
    Question<Boolean> question = baseYesNoQuestion3();
    question.getSharedFields().setOptionalNextQuestion(form -> of(QuestionMocks.baseYesNoQuestion4()) );
    return question;
  }

  public static Question<Boolean> nestedSubquestions1() {
    Question<?> subquestion1 = baseYesNoQuestion3();
    Question<?> subquestion2 = baseYesNoQuestion3();
    subquestion2.getSharedFields().setKey(subquestion2.getKey()+"_2");

    Question<Boolean> question = baseYesNoQuestion1();
    subquestion1.getSharedFields().setOptionalNextQuestion(form -> Optional.of(subquestion2) );
    question.getSharedFields().setOptionalNextQuestion(form -> Optional.of(subquestion1) );
    return question;
  }

  public static Question<Boolean> nestedSubquestions2() {
    Question<?> subquestion1 = baseYesNoQuestion4();
    Question<?> subquestion2 = baseYesNoQuestion4();
    subquestion2.getSharedFields().setKey(subquestion2.getKey()+"_2");

    Question<Boolean> question = baseYesNoQuestion2();
    subquestion1.getSharedFields().setOptionalNextQuestion(form -> Optional.of(subquestion2) );
    question.getSharedFields().setOptionalNextQuestion(form -> Optional.of(subquestion1) );
    return question;
  }

}
