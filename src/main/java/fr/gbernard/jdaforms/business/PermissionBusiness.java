package fr.gbernard.jdaforms.business;

import fr.gbernard.jdaforms.model.Form;

public class PermissionBusiness {

  public static boolean userAllowedAnswer(long userId, Form form) {
    return userId == form.getUserId();
  }

}
