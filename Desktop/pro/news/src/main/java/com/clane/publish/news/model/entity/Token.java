package com.clane.publish.news.model.entity;

import com.clane.publish.news.model.constants.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author Abdussamad
 */
@Entity
public class Token implements Serializable {

  private int id;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp expiresAt;
  private Status status;
  private String token;
  private String author;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Basic
  @Column(name = "created_at", nullable = false)
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  @Basic
  @Column(name = "updated_at", nullable = true)
  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Basic
  @Column(name = "expires_at", nullable = true)
  public Timestamp getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Timestamp expiresAt) {
    this.expiresAt = expiresAt;
  }

  @Basic
  @Column(name = "status", nullable = true, length = 16)
  @Enumerated(EnumType.STRING)
  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Basic
  @Column(name = "token", nullable = false, length = 64)
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @PrePersist
  public void beforeSave() {
    this.createdAt = new Timestamp(new Date().getTime());
  }

  @PreUpdate
  private void beforeUpdate() {
    this.updatedAt = new Timestamp(new Date().getTime());
  }

  @Basic
  @Column(name = "author", nullable = false, length = 32)
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Token token1 = (Token) o;
    return id == token1.id &&
            Objects.equals(createdAt, token1.createdAt) &&
            Objects.equals(updatedAt, token1.updatedAt) &&
            Objects.equals(expiresAt, token1.expiresAt) &&
            status == token1.status &&
            Objects.equals(token, token1.token) &&
            Objects.equals(author, token1.author);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, createdAt, updatedAt, expiresAt, status, token, author);
  }
}
