package com.clane.publish.news.Service;

import com.clane.publish.news.Repository.NewsRepository;
import com.clane.publish.news.exception.NotFoundException;
import com.clane.publish.news.exception.ProcessingException;
import com.clane.publish.news.model.entity.Author;
import com.clane.publish.news.model.entity.News;
import com.clane.publish.news.model.request.UpsertNewsPublicationRequest;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Kolawole
 */
@Service
public class NewsService {

    private NewsRepository newsRepository;
    private final String UPLOADS_FILE_PATH = "files//";

    public NewsService(NewsRepository newsRepository) {
        Assert.notNull(newsRepository);
        this.newsRepository = newsRepository;
    }

    public News publishNews(
            UpsertNewsPublicationRequest upsertNewsPublicationRequest, Author authorPublishingNews
    ) throws Exception {
        News newsToPublish = new News();
        File attachedFile = null;
        if (upsertNewsPublicationRequest.getFile() != null) {
            String uploadedFile = convertMultipartFileToFile(upsertNewsPublicationRequest, attachedFile,
                    authorPublishingNews);
            newsToPublish.setAuthor(authorPublishingNews.getUniqueKey());
            newsToPublish.setStatus(upsertNewsPublicationRequest.getStatus());
            newsToPublish.setDescription(upsertNewsPublicationRequest.getDescription());
            newsToPublish.setFile(uploadedFile);
            generateUniqueKey(newsToPublish);
        }

        return newsRepository.save(newsToPublish);
    }

    public News prepareNewsForUpdate(
            UpsertNewsPublicationRequest upsertNewsPublicationRequest, String newsKey, Author user
    ) throws Exception {
        News newsToUpdate = newsRepository.findByUniqueKey(newsKey);
        if (!newsToUpdate.getAuthor().equals(user.getUniqueKey())) {
            throw new ProcessingException("Author cannot update article of other authors");
        }
        File attachedFile = null;
        if (upsertNewsPublicationRequest.getFile() != null) {
            String uploadedFile = convertMultipartFileToFile(upsertNewsPublicationRequest, attachedFile,
                    user);
            newsToUpdate.setStatus(upsertNewsPublicationRequest.getStatus());
            newsToUpdate.setDescription(upsertNewsPublicationRequest.getDescription());
            newsToUpdate.setFile(uploadedFile);
        }
        return newsRepository.save(newsToUpdate);
    }

    public List<News> deleteNews(
          String newsKey, Author user
    ) throws Exception {
        List<News> savedNews = newsRepository.findByAuthor(user.getUniqueKey());
        News newsToDelete = savedNews
                .stream()
                .filter(news-> news.getUniqueKey().equals(newsKey))
                .findAny()
                .orElse(null);
        if(newsToDelete == null){
           throw new NotFoundException("News article does not exist for this Author");
        }
        newsRepository.delete(newsToDelete);
        List<News> remainingNews = newsRepository.findByAuthor(user.getUniqueKey());
        return remainingNews;
    }

    public List<News> getAllPublications(){
        List<News> savedNews = newsRepository.findAll();
        return savedNews;
    }

    private String convertMultipartFileToFile(
            UpsertNewsPublicationRequest upsertNewsPublicationRequest, File attachedFile,
            Author author)
            throws Exception {
        attachedFile = new File(
                UPLOADS_FILE_PATH + author.getUniqueKey() + "-" + upsertNewsPublicationRequest.getFile()
                        .getOriginalFilename());
        attachedFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(attachedFile);
        fos.write(upsertNewsPublicationRequest.getFile().getBytes());
        fos.close();
        upsertNewsPublicationRequest.setAttachedFile(attachedFile);
        attachedFile.getAbsolutePath();
        return String.valueOf(upsertNewsPublicationRequest.getAttachedFile());
    }

    private void generateUniqueKey(News news) throws ProcessingException {
        try {
            String rawKey = news.getDescription() + LocalDateTime.now();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] encrypted = md.digest(rawKey.getBytes());
            String uniqueKey = new String(Hex.encodeHex(encrypted));
            news.setUniqueKey(uniqueKey);
        } catch (NoSuchAlgorithmException ex) {
            String errorMessage = "Unable to create unique News key";
            throw new ProcessingException(errorMessage);
        }
    }
}
