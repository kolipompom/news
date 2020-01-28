package com.clane.publish.news.Controller;

import com.clane.publish.news.Service.facade.AccountFacade;
import com.clane.publish.news.model.entity.Author;
import com.clane.publish.news.model.request.UpsertNewsPublicationRequest;
import com.clane.publish.news.model.response.NewsApiResponse;
import com.clane.publish.news.model.response.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

/**
 * @author Kolawole
 */
@RestController
@RequestMapping(path = "/publications")
public class PublicationController {

    private final AccountFacade accountFacade;
    Logger logger = LoggerFactory.getLogger(PublicationController.class);

    public PublicationController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping( method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<NewsApiResponse> toPublish(
            @RequestHeader("S-Author-Token") String userToken,
            @ModelAttribute UpsertNewsPublicationRequest upsertNewsPublicationRequest) throws Exception {
        Author user = accountFacade.getAuthenticatedUser(userToken);
        logger.info("Request for file upload" + upsertNewsPublicationRequest);
        SuccessResponse successResponse = accountFacade.publishNews(
                upsertNewsPublicationRequest, user);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(path = "/{newsKey}", method = RequestMethod.PUT,
            produces = "application/json")
    public ResponseEntity<NewsApiResponse> toEditPublication(
            @RequestHeader("S-Author-Token") String userToken,  @PathVariable String newsKey,
            @ModelAttribute UpsertNewsPublicationRequest upsertNewsPublicationRequest) throws Exception {
        Author user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.updatePublication(
                upsertNewsPublicationRequest, user, newsKey);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = "/{newsKey}", method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity<NewsApiResponse> toDeletePublication(
            @RequestHeader("S-Author-Token") String userToken,
            @PathVariable String newsKey
    ) throws Exception {
        Author user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.deletePublication(user, newsKey);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping( method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<NewsApiResponse> toGetAllPublications(
    ) throws Exception {
        SuccessResponse successResponse = accountFacade.getAllPublication();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
