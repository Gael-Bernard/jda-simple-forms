package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.utils.ExceptionUtils;
import lombok.NonNull;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Question<T> {

  @NonNull Function<Form, Optional<Question<?>>> getOptionalNextQuestion();
  @NonNull String getKey();
  @NonNull Optional<T> getAnswer();
  void setOptionalNextQuestion(@NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion);
  void setKey(@NonNull String key);
  void setAnswer(@NonNull Optional<T> answer);

  void editQuestionMessage(InteractionHook hookToMessage, Form form) throws Exception;
  T parseAnswer(List<String> discordReturnedValues) throws Exception;

  default void parseAndSetAnswer(List<String> answer) {
    Optional<T> answerOpt = Optional.of( ExceptionUtils.uncheck(() -> this.parseAnswer(answer) ));
    this.setAnswer(answerOpt);
  }

}
