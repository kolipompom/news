package com.clane.publish.news.exception;

/**
 * @author Kolawole
 */
public abstract class NewsApiException extends RuntimeException {

  NewsApiException(String message) {
    super(message);
  }

  NewsApiException(String message, Throwable cause) {
    super(message, cause);
    if (this.getCause() == null && cause != null) {
      this.initCause(cause);
    }
  }
}
