package com.laurynas.workorder.validationrest.repository.model;

import com.laurynas.workorder.validationrest.WorkOrderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
public class ValidationEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Setter(AccessLevel.NONE)
    private OffsetDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private WorkOrderType type;

    private String department;

    private boolean valid;

    @PrePersist
    void setUpTimestamp() {
        timestamp = OffsetDateTime.now();
    }
}
