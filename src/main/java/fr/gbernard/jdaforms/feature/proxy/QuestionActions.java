package fr.gbernard.jdaforms.feature.proxy;

import fr.gbernard.jdaforms.business.QuestionCompletionBusiness;
import fr.gbernard.jdaforms.controller.defaultmessages.DefaultMessagesEditors;
import fr.gbernard.jdaforms.exception.NoAnswerException;
import fr.gbernard.jdaforms.exception.NoCurrentQuestionException;
import fr.gbernard.jdaforms.feature.FormContinueFeature;
import fr.gbernard.jdaforms.model.Form;
import fr.gbernard.jdaforms.model.FormMessageEditor;
import fr.gbernard.jdaforms.model.Question;
import lombok.*;
import net.dv8tion.jda.api.interactions.InteractionHook;

@Getter
@Builder
@RequiredArgsConstructor
public class QuestionActions {

  @Getter(AccessLevel.NONE)
  private final @NonNull FormContinueFeature formContinueFeature;
  @Getter(AccessLevel.NONE)
  private final @NonNull QuestionCompletionBusiness questionCompletionBusiness;

  private final @NonNull Form form;
  private final @NonNull InteractionHook hook;

  /**
   * Refreshes the form message in Discord for the user
   */
  public void refreshCurrentQuestion() {
    formContinueFeature.refreshFormWithCurrentQuestion(hook, form);
  }

  /**
   * Sets the answer for current question, marks as complete, and moves on to the next question
   * @param answer parsed answer given by user
   */
  public <T> void answerAndStartNextQuestion(T answer) {
    formContinueFeature.saveAnswerAndSendNextQuestion(hook, form, answer);
  }

  /**
   * Moves on to the next question
   * <br>This method requires the current question's answer to be defined.
   */
  public void startNextQuestionWithoutAnswering() throws NoCurrentQuestionException, NoAnswerException {
    final Question<?> currentQuestion = form.getCurrentQuestion()
        .orElseThrow(() -> new NoCurrentQuestionException("Cannot start the next question if the form doesn't have a current question"));

    currentQuestion.getAnswer()
        .orElseThrow(() -> new NoAnswerException("Cannot move to the next question before the current question has an answer"));

    formContinueFeature.sendNextQuestion(hook, form);
  }

  /**
   * Discards all the data about the form, making it unusable
   */
  public void cancelForm() {
    formContinueFeature.cancelForm(form);
  }

}
