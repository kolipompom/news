package com.clane.publish.news.model.request;

import com.clane.publish.news.model.constants.Status;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

/**
 * @author Kolawole
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpsertNewsPublicationRequest {

    private String description;
    private MultipartFile file;
    private File attachedFile;
    private Status status;
}
