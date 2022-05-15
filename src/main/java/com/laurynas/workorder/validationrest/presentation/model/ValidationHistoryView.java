package com.laurynas.workorder.validationrest.presentation.model;

import com.laurynas.workorder.validationrest.WorkOrderType;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ValidationHistoryView {

    private OffsetDateTime timestamp;
    private WorkOrderType type;
    private String department;
    private boolean valid;
}
