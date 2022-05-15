package com.laurynas.workorder.validationrest.presentation.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.laurynas.workorder.validationrest.WorkOrderType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonTypeInfo(
        include = JsonTypeInfo.As.PROPERTY,
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AnalysisWorkOrderView.class, name = "ANALYSIS"),
        @JsonSubTypes.Type(value = RepairWorkOrderView.class, name = "REPAIR"),
        @JsonSubTypes.Type(value = ReplacementWorkOrderView.class, name = "REPLACEMENT")
})
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public abstract class WorkOrderView {

    private WorkOrderType type;
    private String department;
    private LocalDate startDate;
    private LocalDate endDate;
    private String currency;
    private BigDecimal cost;
    private List<PartView> parts;

}
