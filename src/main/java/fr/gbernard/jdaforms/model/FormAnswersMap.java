package fr.gbernard.jdaforms.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FormAnswersMap {

  private final Map<String, Object> answers;

  public FormAnswersMap(Map<String, Object> answers) {
    this.answers = new HashMap<>(answers);
  }

  public Set<String> keys() {
    return answers.keySet();
  }

  public Collection<Object> values() {
    return answers.values();
  }

  public boolean getAsBoolean(String key) {
    return (Boolean) this.answers.get(key);
  }

  public int getAsInt(String key) {
    return (Integer) this.answers.get(key);
  }

  public long getAsLong(String key) {
    return (Long) this.answers.get(key);
  }

  public String getAsString(String key) {
    return (String) this.answers.get(key);
  }

  public Object getAsObject(String key) {
    return this.answers.get(key);
  }

  public <T> T getAs(String key, Class<T> clazz) {
    return (T) this.answers.get(key);
  }

}
