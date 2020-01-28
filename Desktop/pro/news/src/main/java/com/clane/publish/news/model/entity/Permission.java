package com.clane.publish.news.model.entity;

import com.clane.publish.news.model.constants.Status;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Kolawole
 */
@Entity
@Table(name = "permission")
public class Permission implements Serializable {

  private Integer id;
  private String name;
  private String description;
  private String code;
  private Status status;
  private Status isHidden;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Basic
  @Column(name = "name", nullable = false, length = 128)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "description", nullable = true)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Basic
  @Column(name = "code", nullable = false, length = 256)
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Basic
  @Column(name = "status", nullable = false, length = 16)
  @Enumerated(value = EnumType.STRING)
  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Basic
  @Column(name = "is_hidden", nullable = false, length = 16)
  @Enumerated(value = EnumType.STRING)
  public Status getIsHidden() {
    return isHidden;
  }

  public void setIsHidden(Status isHidden) {
    this.isHidden = isHidden;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Permission permission = (Permission) o;

    if (id != permission.id) {
      return false;
    }
    if (name != null ? !name.equals(permission.name) : permission.name != null) {
      return false;
    }
    if (description != null ? !description.equals(permission.description)
        : permission.description != null) {
      return false;
    }
    if (code != null ? !code.equals(permission.code) : permission.code != null) {
      return false;
    }
    if (status != null ? !status.equals(permission.status) : permission.status != null) {
      return false;
    }
    return isHidden.equals(permission.isHidden);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (code != null ? code.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (isHidden != null ? isHidden.hashCode() : 0);
    return result;
  }

}
