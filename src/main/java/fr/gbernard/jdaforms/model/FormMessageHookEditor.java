package fr.gbernard.jdaforms.model;

import net.dv8tion.jda.api.interactions.InteractionHook;

@FunctionalInterface
public interface FormMessageHookEditor {

  void edit(InteractionHook hookToMessage, Form form) throws Exception;

}
