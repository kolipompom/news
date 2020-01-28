package com.clane.publish.news.model.request;

import com.clane.publish.news.model.constants.Status;
import com.clane.publish.news.model.entity.Author;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Kolawole
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpsertAuthorRequest {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String phone;
    private String roleId;
    private Status status;


    public Author toUser() {
        Author author = new Author();
        author.setAddress(address);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setEmail(email);
        author.setPassword(password);
        author.setPhone(phone);
        author.setRoleId(roleId);
        if (status != null) {
            author.setStatus(status);
        }
        return author;
    }
}
