package fr.gbernard.jdaforms.service;

import fr.gbernard.jdaforms.model.Form;

public class PermissionService {

  public static boolean userAllowedAnswer(long userId, Form form) {
    return userId == form.getUserId();
  }

}
