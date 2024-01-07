package fr.gbernard.jdaforms.model;

import net.dv8tion.jda.api.interactions.callbacks.IMessageEditCallback;

@FunctionalInterface
public interface FormMessageEditor {

  void edit(IMessageEditCallback message, Form form) throws Exception;

}
