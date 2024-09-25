package com.openclassrooms.mdd.auth_api.model;

import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
@Table(value = "users")
public class UserEntity {
    @Id
    protected Long id;

    @NonNull
    @NotBlank
    @Size(max = 255)
    protected String name;

    @NonNull
    @NotBlank
    @Email
    @Size(max = 255)
    protected String email;

    @NonNull
    @NotBlank
    @Size(max = 255)
    protected String password;

    @Column("created_at")
    @CreatedDate
    protected LocalDateTime createdAt;

    @Column("updated_at")
    @LastModifiedDate
    protected LocalDateTime updatedAt;
}
