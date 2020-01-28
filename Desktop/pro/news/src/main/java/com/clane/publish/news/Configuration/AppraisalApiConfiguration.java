package com.clane.publish.news.Configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Kolawole
 */
@AllArgsConstructor
@Builder
@Component
@ConfigurationProperties(prefix = "clane")
@Data
@NoArgsConstructor
public class AppraisalApiConfiguration {
}
