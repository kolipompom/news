package com.clane.publish.news.exception;

/**
 * @author Kolawole
 */
public class BadRequestException extends NewsApiException {

  public BadRequestException(String message) {
    super(message);
  }
}
