package com.example.SpringAPI.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    UUID id;
    @LastModifiedDate
    LocalDateTime lastModifiedDate;
    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdDate;
}
