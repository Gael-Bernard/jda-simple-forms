package fr.gbernard.jdaforms.service;

import fr.gbernard.jdaforms.model.Form;
import mocks.service.FormMocks;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionServiceTest_userAllowedAnswer {

  final static long USER_ID_1 = 1015718412074365009L;
  final static long USER_ID_2 = 372425657269092350L;

  @Test
  void allowedReturnsTrue() {
    Form form = FormMocks.initialised2YesNoQuestions();
    form.setUserId(USER_ID_1);
    long interactingUserId = USER_ID_1;
    assertTrue( PermissionService.userAllowedAnswer(interactingUserId, form) );
  }

  @Test
  void differentUserInteractingReturnsFalse() {
    Form form = FormMocks.initialised2YesNoQuestions();
    form.setUserId(USER_ID_1);
    long interactingUserId = USER_ID_2;
    assertFalse( PermissionService.userAllowedAnswer(interactingUserId, form) );
  }

}