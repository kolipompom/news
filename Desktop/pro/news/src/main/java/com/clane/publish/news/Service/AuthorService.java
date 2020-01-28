package com.clane.publish.news.Service;

import com.clane.publish.news.Repository.RoleRepository;
import com.clane.publish.news.Repository.TokenRepository;
import com.clane.publish.news.Repository.AuthorRepository;
import com.clane.publish.news.exception.*;
import com.clane.publish.news.model.constants.Status;
import com.clane.publish.news.model.entity.Author;
import com.clane.publish.news.model.entity.Role;
import com.clane.publish.news.model.entity.Token;
import com.clane.publish.news.util.SecurityUtil;
import com.clane.publish.news.util.TimeUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kolawole
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private static final String NO_PASSWORD = "password may not be null or empty";
    private static final int SESSION_LIFE = 300;
    private static final String USER_NOT_FOUND = "Author not found";
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;

    public AuthorService(AuthorRepository authorRepository,
                         RoleRepository roleRepository,
                         TokenRepository tokenRepository){
        Assert.notNull(authorRepository);
        Assert.notNull(roleRepository);
        Assert.notNull(tokenRepository);
        this.roleRepository = roleRepository;
        this.tokenRepository=tokenRepository;
        this.authorRepository = authorRepository;
    }

    public Author createUser(Author author){
        prepareUserForCreation(author);
        validateUserForCreation(author);
        setUserStatus(author);
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        List<Author> savedAuhtors = authorRepository.findAll();
        return savedAuhtors;
    }

    public void logout(String tokenValue) {
        Token token = tokenRepository.findOneByToken(tokenValue);
        token.setStatus(Status.INACTIVE);
        tokenRepository.save(token);
    }

    public Author authenticateUser(Author author) throws AuthenticationException {
        Author savedAuthor = authorRepository.findOneByEmail(author.getEmail());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if ((savedAuthor == null)
                || !encoder.matches(
                author.getPassword() + savedAuthor.getUniqueKey(), savedAuthor.getPassword())) {
            throw new AuthenticationException("Author not authenticated");
        } else if (savedAuthor != null) {
            if (savedAuthor.getStatus() == Status.NEW) {
                throw new AuthenticationException(
                        "Author yet to complete registration. Kindly, check your mail and complete the registration");
            } else if (savedAuthor.getStatus() == Status.INACTIVE) {
                throw new AuthenticationException(
                        "Your account has been deactivated. Kindly, contact admin");
            }
        }
        savedAuthor.setLastLoginDate(TimeUtil.now());
        return savedAuthor;
    }

    public Author getAuthenticatedUser(String token) throws AuthenticationException {
        Timestamp now = new Timestamp(new Date().getTime());
        Token userToken = tokenRepository.findOneByToken(token);
        if (userToken == null ||
                userToken.getExpiresAt().before(now) ||
                userToken.getStatus() != Status.ACTIVE) {
            if (userToken != null && userToken.getStatus() == Status.ACTIVE) {
                userToken.setStatus(Status.INACTIVE);
                tokenRepository.save(userToken);
            }
            throw new AuthenticationException("Author not authenticated");
        }
        extendSession(userToken);
        return authorRepository.findOneByUniqueKey(userToken.getAuthor());
    }

    public Token createToken(Author author) throws ProcessingException {
        String rawKey = author.getUniqueKey() + new Date().toString();
        String tokenValue = SecurityUtil.hashWithMd5(rawKey);
        Token token = new Token();
        token.setStatus(Status.ACTIVE);
        token.setToken(tokenValue);
        token.setAuthor(author.getUniqueKey());
        token.setExpiresAt(TimeUtil.futureTime(SESSION_LIFE));
        return tokenRepository.save(token);
    }

    public Author fetchByUniqueKey(String uniqueKey) {
        Author author = authorRepository.findOneByUniqueKey(uniqueKey);
        if (author == null) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return author;
    }

    private void prepareUserForSave(Author author) throws NewsApiException {
        normalizePhone(author);
    }

    private void prepareUserForUpdate(Author author) {
        author.setPassword(null);
    }

    private void prepareUserForCreation(Author author) throws NewsApiException {
        generateUniqueKey(author);
        normalizePhone(author);
        hashPassword(author);
    }

    private void generateUniqueKey(Author author) throws ProcessingException {
        if (author.getUniqueKey() != null) {
            return;
        }
        String rawKey = author.getEmail() + LocalDateTime.now() + Math.random();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        author.setUniqueKey(uniqueKey);
    }

    private void normalizePhone(Author author) throws BadRequestException {
        if (author.getPhone() == null || author.getPhone().isEmpty()) {
            return;
        }
    }

    private void hashPassword(Author author) {
        if (author.getPassword() == null) {
            author.setPassword(NO_PASSWORD);
            return;
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(author.getPassword() + author.getUniqueKey());
        author.setPassword(encodedPassword);
    }

    private void validateUserForCreation(Author author) throws ConflictException {
        verifyEmailIsUnique(author);
        verifyPhoneIsUnique(author);
        verifyRoleId(author);
    }

    private void verifyRoleId(Author author) throws NotFoundException {
        Role savedRole = roleRepository.findOneByUniqueKey(author.getRoleId());
        if (savedRole == null) {
            throw new NotFoundException("Role doesn't exist");
        }

    }

    private void extendSession(Token token) {
        LocalDateTime tokenExpiryTime = token.getExpiresAt().toLocalDateTime();
        if (ChronoUnit.SECONDS.between(LocalDateTime.now(), tokenExpiryTime) < SESSION_LIFE / 2) {
            token.setExpiresAt(TimeUtil.futureTime(SESSION_LIFE));
            tokenRepository.save(token);
        }
    }

    private void setUserStatus(Author author) {
        author.setStatus(Status.ACTIVE);
    }

    private void verifyEmailIsUnique(Author author) throws ConflictException {
        Author savedAuthor = authorRepository.findOneByEmail(author.getEmail());
        if (savedAuthor != null) {
            throw new ConflictException("Author with this email already exists");
        }
    }

    private void verifyPhoneIsUnique(Author author) throws ConflictException {
        if (author.getPhone() == null || author.getPhone().isEmpty()) {
            return;
        }
        Author savedAuthor = authorRepository.findOneByPhone(author.getPhone());
        if (savedAuthor != null) {
            throw new ConflictException("Author with this phone number already exists");
        }
    }
}
