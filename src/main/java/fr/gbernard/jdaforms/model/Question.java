package fr.gbernard.jdaforms.model;

import lombok.NonNull;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Question<T> {

  @NonNull Function<Form, Optional<Question<?>>> optionalNextQuestion = ci -> Optional.empty();
  @NonNull String getKey();
  @NonNull Optional<T> getAnswer();

  void editQuestionMessage(InteractionHook hookToMessage, Form form) throws Exception;
  T parseAnswer(List<String> discordReturnedValues) throws Exception;
}
