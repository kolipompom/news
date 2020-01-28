package com.clane.publish.news.Controller;

import com.clane.publish.news.Service.facade.AccountFacade;
import com.clane.publish.news.exception.NewsApiException;
import com.clane.publish.news.model.entity.Author;
import com.clane.publish.news.model.request.AuthenticateUserRequest;
import com.clane.publish.news.model.request.UpsertAuthorRequest;
import com.clane.publish.news.model.response.NewsApiResponse;
import com.clane.publish.news.model.response.SuccessResponse;
import com.clane.publish.news.validator.InputValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Kolawole
 */
@RestController
@RequestMapping(path = "/authors")
public class AuthorController {

    private final AccountFacade accountFacade;

    public AuthorController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<NewsApiResponse> toCreateUser(
            @Valid @RequestBody UpsertAuthorRequest upsertAuthorRequest,
            BindingResult bindingResult) {
        InputValidator.validate(bindingResult);
        SuccessResponse response = accountFacade.createUser(upsertAuthorRequest);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<NewsApiResponse> getAllAuthors( ) {
        SuccessResponse successResponse =
                                accountFacade.getAllAuthors();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/authenticate",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<NewsApiResponse> authenticate(
            @Valid @RequestBody AuthenticateUserRequest request,
            BindingResult bindingResult) throws NewsApiException {
        InputValidator.validate(bindingResult);
        SuccessResponse response = accountFacade.authenticateUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/logout",
            produces = "application/json")
    public ResponseEntity<NewsApiResponse> logout(
            @RequestHeader("S-User-Token") String userToken
    ) {
        Author author = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse response = accountFacade.logoutUser(author, userToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}