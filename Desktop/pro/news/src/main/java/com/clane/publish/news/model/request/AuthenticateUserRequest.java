package com.clane.publish.news.model.request;

import com.clane.publish.news.model.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Kolawole
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateUserRequest {

  @NotNull
  @NotEmpty
  private String email;

  @NotNull
  @NotEmpty
  private String password;

  public Author toUser() {
    Author author = new Author();
    author.setEmail(email);
    author.setPassword(password);
    return author;
  }
}
