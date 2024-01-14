package fr.gbernard.jdaforms.exception;

public class NonCompletedQuestionException extends RuntimeException {

  public NonCompletedQuestionException(String message) {
    super(message);
  }

}
