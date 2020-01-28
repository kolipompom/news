package com.clane.publish.news.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author Kolawole
 */
public class RandomGenerator {

  private static Random random = new Random();

  public static String generateId() {
    long id = 10000000000000000L;
    id += System.currentTimeMillis();
    id += random.nextInt();
    return String.valueOf(id);
  }

  public static String generatePaymentReference() {
    DateTimeFormatter date = DateTimeFormatter.ofPattern("MMyydd");
    DateTimeFormatter time = DateTimeFormatter.ofPattern("HHmmss");
    LocalDateTime now = LocalDateTime.now();
    Random random = new Random();
    return "XPS/" + date.format(now) + "/" + time.format(now) + now.getNano() + String
        .format("%02d", random.nextInt(10000000));
  }
}
