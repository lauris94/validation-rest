package com.laurynas.workorder.validationrest.presentation.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RepairWorkOrderView extends WorkOrderView {

    private LocalDate analysisDate;
    private String responsiblePerson;
    private LocalDate testDate;
}
