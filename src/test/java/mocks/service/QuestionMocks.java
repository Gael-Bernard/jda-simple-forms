package mocks.service;

import fr.gbernard.jdaforms.controller.question.yesno.YesNoQuestion;
import fr.gbernard.jdaforms.model.Question;

import java.util.Optional;

import static java.util.Optional.of;

public class QuestionMocks {

  public static Question<Boolean> baseYesNoQuestion1() {
    return YesNoQuestion.builder()
        .key("is-raclette")
        .title("Do you know raclette?")
        .subtitle("Have you ever heard of raclette? If not, you're missing something for sure!")
        .build();
  }

  public static Question<Boolean> baseYesNoQuestion2() {
    return YesNoQuestion.builder()
        .key("cats")
        .title("Do you prefer cats over dogs?")
        .subtitle("Please tell us if you prefer cats over dogs")
        .build();
  }

  public static Question<Boolean> baseYesNoQuestion3() {
    return YesNoQuestion.builder()
        .key("liechtenstein-smallest-europe")
        .title("Is Liechtenstein the smallest country in Europe?")
        .subtitle("According to your knowledge, is the **Principality of Liechtenstein** the UN recognised country with the least land in Europe")
        .build();
  }

  public static Question<Boolean> baseYesNoQuestion4() {
    return YesNoQuestion.builder()
        .key("camels")
        .title("Do you prefer camels over cats?")
        .subtitle("Please tell us if you prefer camels over cats")
        .build();
  }

  public static Question<Boolean> simpleSubquestion1() {
    Question<Boolean> question = baseYesNoQuestion1();
    question.setOptionalNextQuestion(form -> of(QuestionMocks.baseYesNoQuestion2()) );
    return question;
  }

  public static Question<Boolean> simpleSubquestion2() {
    Question<Boolean> question = baseYesNoQuestion3();
    question.setOptionalNextQuestion(form -> of(QuestionMocks.baseYesNoQuestion4()) );
    return question;
  }

  public static Question<Boolean> nestedSubquestions1() {
    Question<?> subquestion1 = baseYesNoQuestion3();
    Question<?> subquestion2 = baseYesNoQuestion3();
    subquestion2.setKey(subquestion2.getKey()+"_2");

    Question<Boolean> question = baseYesNoQuestion1();
    subquestion1.setOptionalNextQuestion(form -> Optional.of(subquestion2) );
    question.setOptionalNextQuestion(form -> Optional.of(subquestion1) );
    return question;
  }

  public static Question<Boolean> nestedSubquestions2() {
    Question<?> subquestion1 = baseYesNoQuestion4();
    Question<?> subquestion2 = baseYesNoQuestion4();
    subquestion2.setKey(subquestion2.getKey()+"_2");

    Question<Boolean> question = baseYesNoQuestion2();
    subquestion1.setOptionalNextQuestion(form -> Optional.of(subquestion2) );
    question.setOptionalNextQuestion(form -> Optional.of(subquestion1) );
    return question;
  }

}
