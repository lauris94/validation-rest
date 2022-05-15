package com.laurynas.workorder.validationrest.presentation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplacementWorkOrderView extends WorkOrderView {

    private String factoryName;
    private String factoryOrderNumber;
}
