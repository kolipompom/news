package com.clane.publish.news.exception;

/**
 * @author Kolawole
 */
public class ProcessingException extends NewsApiException {

  public ProcessingException(String message) {
    super(message);
  }

  public ProcessingException(String message, Throwable cause) {
    super(message, cause);
  }
}
