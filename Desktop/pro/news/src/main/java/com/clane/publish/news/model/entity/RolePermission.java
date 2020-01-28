package com.clane.publish.news.model.entity;

import com.clane.publish.news.model.constants.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Abdussamad
 */
@Entity
@Table(name = "role_permission")
public class RolePermission implements Serializable {

  private Integer id;
  private Integer roleId;
  private Integer permissionId;
  private Status status;
  private Timestamp createdAt;
  private Timestamp updatedAt;

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
  @Column(name = "role_id", nullable = false)
  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  @Basic
  @Column(name = "permission_id", nullable = false)
  public Integer getPermssionId() {
    return permissionId;
  }

  public void setPermssionId(Integer permssionId) {
    this.permissionId = permssionId;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RolePermission rolePermission = (RolePermission) o;

    if (id != rolePermission.id) {
      return false;
    }
    if (roleId != null ? !roleId.equals(rolePermission.roleId) : rolePermission.roleId != null) {
      return false;
    }
    if (permissionId != null ? !permissionId.equals(rolePermission.permissionId)
        : rolePermission.roleId != null) {
      return false;
    }
    if (status != null ? !status.equals(rolePermission.status) : rolePermission.status != null) {
      return false;
    }
    if (!createdAt.equals(rolePermission.createdAt)) {
      return false;
    }
    return updatedAt.equals(rolePermission.updatedAt);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
    result = 31 * result + (permissionId != null ? permissionId.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    return result;
  }

  @PrePersist
  public void beforeSave() {
    this.createdAt = new Timestamp(new Date().getTime());
  }

  @PreUpdate
  private void beforeUpdate() {
    this.updatedAt = new Timestamp(new Date().getTime());
  }

}
