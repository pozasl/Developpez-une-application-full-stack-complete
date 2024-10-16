package com.openclassrooms.mdd.users_api.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * User entity
 */
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Table(value = "users")
public class UserEntity {

    @Id
    private Long id;

    @NonNull
    @NotBlank
    @Size(max=255)
    private String name;
    
    @NonNull
    @NotBlank
    @Email
    @Size(max=255)
    private String email;

    @NonNull
    @NotBlank
    @Size(max=255)
    private String password;

    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
