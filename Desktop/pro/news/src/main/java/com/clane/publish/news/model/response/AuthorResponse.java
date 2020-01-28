package com.clane.publish.news.model.response;

import com.clane.publish.news.model.constants.Status;
import com.clane.publish.news.model.entity.Author;
import com.clane.publish.news.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kolawole
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AuthorResponse {

  private String uniqueKey;
  private String firstName;
  private String lastName;
  private String email;
  private String address;
  private String roleId;
  private String phone;
  private Status status;
  private String lastLoginDate;
  private String roleName;
  private String createdAt;
  private String updatedAt;

  public static AuthorResponse fromAuthor(Author author) {
    if (author == null) {
      return null;
    }
    return AuthorResponse.builder()
        .uniqueKey(author.getUniqueKey())
        .firstName(author.getFirstName())
        .lastName(author.getLastName())
        .email(author.getEmail())
        .roleId(author.getRoleId())
        .phone(author.getPhone())
        .address(author.getAddress())
        .status(author.getStatus())
        .lastLoginDate(TimeUtil.getIsoTime(author.getLastLoginDate()))
        .createdAt(TimeUtil.getIsoTime(author.getCreatedAt()))
        .updatedAt(TimeUtil.getIsoTime(author.getUpdatedAt()))
        .build();
  }

  public static List<AuthorResponse> fromAuthors(List<Author> authors) {
    return authors.stream().map(user -> {
      return fromAuthor(user);
    }).collect(
        Collectors.toList());
  }
}
