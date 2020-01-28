package com.clane.publish.news.Service.facade;

import com.clane.publish.news.Service.NewsService;
import com.clane.publish.news.Service.RoleService;
import com.clane.publish.news.Service.AuthorService;
import com.clane.publish.news.model.entity.Author;
import com.clane.publish.news.model.entity.News;
import com.clane.publish.news.model.entity.Role;
import com.clane.publish.news.model.entity.Token;
import com.clane.publish.news.model.request.AuthenticateUserRequest;
import com.clane.publish.news.model.request.UpsertAuthorRequest;
import com.clane.publish.news.model.request.UpsertNewsPublicationRequest;
import com.clane.publish.news.model.response.AuthResponse;
import com.clane.publish.news.model.response.NewsPublicationResponse;
import com.clane.publish.news.model.response.SuccessResponse;
import com.clane.publish.news.model.response.AuthorResponse;
import com.clane.publish.news.util.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kolawole
 */
@Component
public class AccountFacade extends RequestFacade {

    private final AuthorService authorService;
    private final RoleService roleService;
    private final NewsService newsService;
    private final PermissionHandler permissionHandler;
    private static final Integer AUTHOR_ROLE_ID = 1;

    public AccountFacade(AuthorService authorService,
                         RoleService roleService,
                         PermissionHandler permissionHandler,
                         NewsService newsService) {
        Assert.notNull(authorService);
        Assert.notNull(roleService);
        Assert.notNull(newsService);
        Assert.notNull(permissionHandler);
        this.roleService = roleService;
        this.authorService = authorService;
        this.permissionHandler = permissionHandler;
        this.newsService = newsService;
    }

    public SuccessResponse createUser(UpsertAuthorRequest upsertAuthorRequest) {
        upsertAuthorRequest.setRoleId(fetchRoleId(upsertAuthorRequest));
        Author author = authorService.createUser(upsertAuthorRequest.toUser());
        return buildSuccessResponse(createUserResponseData(author));
    }

    public Author getAuthenticatedUser(String userToken) {
        return authorService.getAuthenticatedUser(userToken);
    }

    public SuccessResponse getAllAuthors() {
        List<Author> allAuthors = authorService.getAllAuthors();
        Map<String, Object> data = createAuthorResponse(allAuthors);
        return buildSuccessResponse(data);
    }

    public SuccessResponse getAllPublication() {
        List<News> allPublications = newsService.getAllPublications();
        Map<String, Object> data = createPublicationsResponse(allPublications);
        return buildSuccessResponse(data);
    }

    public SuccessResponse authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        Author authorToAuthenticate = authenticateUserRequest.toUser();
        Author authenticatedAuthor = authorService.authenticateUser(authorToAuthenticate);
        Role userRole = roleService.getRole(authenticatedAuthor.getRoleId());
        ArrayList permissions = roleService.getPermissionCodesForRole(userRole.getId());
        Token token = authorService.createToken(authenticatedAuthor);
        Map<String, Object> data = createUserResponseData(authenticatedAuthor);
        data.put("permission", permissions);
        data.put("auth", AuthResponse.fromToken(token));
        return buildSuccessResponse(data);
    }

    public SuccessResponse logoutUser(Author author, String token) {
        authorService.logout(token);
        author.setLastLoginDate(TimeUtil.now());
        return buildSuccessResponse(createUserResponseData(author));
    }

    public SuccessResponse publishNews(
            UpsertNewsPublicationRequest upsertNewsPublicationRequest,
            Author authenticatedUser) throws Exception {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "submit_news");
        News publishedNews = newsService.publishNews(upsertNewsPublicationRequest, authenticatedUser);
        NewsPublicationResponse newsPublicationResponse = NewsPublicationResponse
                .fromPublishedNews(publishedNews);
        Map<String, Object> data = new HashMap<>();
        data.put("news", newsPublicationResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse updatePublication(UpsertNewsPublicationRequest upsertNewsPublicationRequest
            , Author authenticatedUser, String newsKey) throws Exception {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "edit_news");
        News publishedNewsToUpdate = newsService
                .prepareNewsForUpdate(upsertNewsPublicationRequest, newsKey, authenticatedUser);
        NewsPublicationResponse newsPublicationResponse = NewsPublicationResponse
                .fromPublishedNews(publishedNewsToUpdate);
        Map<String, Object> data = new HashMap<>();
        data.put("news", newsPublicationResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse deletePublication(
            Author authenticatedUser, String newsKey
    ) throws Exception {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "delete_news");
        List<News> publishedNewsToUpdate = newsService
                .deleteNews(newsKey, authenticatedUser);
        List<NewsPublicationResponse> newsPublicationResponse = NewsPublicationResponse
                .fromAllPublichedNews(publishedNewsToUpdate);
        Map<String, Object> data = new HashMap<>();
        data.put("news", newsPublicationResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    private String fetchRoleId(UpsertAuthorRequest upsertAuthorRequest) {
        if (upsertAuthorRequest.getRoleId() == null) {
            upsertAuthorRequest.setRoleId(roleService.fetchRoleById(AUTHOR_ROLE_ID).getUniqueKey());
        }
        return upsertAuthorRequest.getRoleId();
    }

    private Map<String, Object> createUserResponseData(Author author) {
        AuthorResponse authorResponse = AuthorResponse.fromAuthor(author);
        Map<String, Object> data = new HashMap<>(1);
        data.put("author", authorResponse);
        return data;
    }

    private Map<String, Object> createPublicationsResponse(List<News> news) {
        List<NewsPublicationResponse> newsPublicationResponse =
                NewsPublicationResponse.fromAllPublichedNews(news);
        Map<String, Object> data = new HashMap<>(1);
        data.put("author", newsPublicationResponse);
        return data;
    }

    private Map<String, Object> createAuthorResponse(List<Author> author) {
        List<AuthorResponse> authorResponse = AuthorResponse.fromAuthors(author);
        Map<String, Object> data = new HashMap<>(1);
        data.put("author", authorResponse);
        return data;
    }
}