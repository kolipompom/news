package com.clane.publish.news.model.response;

import com.clane.publish.news.model.constants.Status;
import lombok.*;

import java.util.Map;

/**
 * @author Kolawole
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SuccessResponse extends NewsApiResponse {

  private Status status;
  private Map<String, Object> data;
}
