package fr.gbernard.jdaforms.model;

import fr.gbernard.jdaforms.feature.proxy.QuestionActions;

import java.util.List;

@FunctionalInterface
public interface FormInteractionHandler {

  void handle(List<String> discordReturnedValues, QuestionActions actions);

}
