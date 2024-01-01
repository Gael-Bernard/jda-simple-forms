package fr.gbernard.jdaforms.feature;

import fr.gbernard.jdaforms.business.PermissionBusiness;
import fr.gbernard.jdaforms.model.Form;
import mocks.service.FormMocks;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionBusinessTest_userAllowedAnswer {

  final static long USER_ID_1 = 1015718412074365009L;
  final static long USER_ID_2 = 372425657269092350L;

  @Test
  void allowedReturnsTrue() {
    Form form = FormMocks.initialised2YesNoQuestions();
    form.setUserId(USER_ID_1);
    long interactingUserId = USER_ID_1;
    assertTrue( PermissionBusiness.userAllowedAnswer(interactingUserId, form) );
  }

  @Test
  void differentUserInteractingReturnsFalse() {
    Form form = FormMocks.initialised2YesNoQuestions();
    form.setUserId(USER_ID_1);
    long interactingUserId = USER_ID_2;
    assertFalse( PermissionBusiness.userAllowedAnswer(interactingUserId, form) );
  }

}