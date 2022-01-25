package com.lime.mediscreenrecord.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "Record")
public class Record implements Persistable<Long> {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Long id;

    private Long patientId;

    @NotBlank(message = "Note is required")
    private String note;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    void createdA() {
        this.createdAt  = (this.createdAt == null) ? new Date() : this.createdAt;
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = new Date();
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}
