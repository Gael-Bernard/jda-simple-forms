package fr.gbernard.jdaforms.repository;

import fr.gbernard.jdaforms.model.Form;

import java.util.HashMap;
import java.util.Optional;

public class OngoingFormsRepository {

  private static final HashMap<Long, Form> forms = new HashMap<>();

  public Optional<Form> getById(long id) {
    return Optional.ofNullable( forms.get(id) );
  }

  public void save(Form form) {
    forms.put(form.getMessageId(), form);
  }

  public void delete(Form form) {
    forms.remove(form.getMessageId());
  }

}
