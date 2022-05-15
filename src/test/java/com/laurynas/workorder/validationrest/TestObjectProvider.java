package com.laurynas.workorder.validationrest;

import com.laurynas.workorder.validationrest.presentation.model.AnalysisWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.PartView;
import com.laurynas.workorder.validationrest.presentation.model.RepairWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.ReplacementWorkOrderView;
import com.laurynas.workorder.validationrest.repository.model.ValidationEntry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestObjectProvider {

    public static ValidationEntry createValidationEntry(long id, WorkOrderType type, String department,
                                                        OffsetDateTime timestamp, boolean isValid) {
        ValidationEntry validationEntry = new ValidationEntry();
        validationEntry.setId(id);
        validationEntry.setType(type);
        validationEntry.setDepartment(department);
        validationEntry.setValid(isValid);
        ReflectionTestUtils.setField(validationEntry, "timestamp", timestamp);
        return validationEntry;
    }

    public static AnalysisWorkOrderView createAnalysisWorkOrderView(String department, LocalDate startDate,
                                                                    LocalDate endDate, String currency, BigDecimal cost,
                                                                    List<PartView> parts) {
        AnalysisWorkOrderView workOrderView = new AnalysisWorkOrderView();
        workOrderView.setType(WorkOrderType.ANALYSIS);
        workOrderView.setDepartment(department);
        workOrderView.setStartDate(startDate);
        workOrderView.setEndDate(endDate);
        workOrderView.setCurrency(currency);
        workOrderView.setCost(cost);
        workOrderView.setParts(parts);
        return workOrderView;
    }

    public static RepairWorkOrderView createRepairWorkOrderView(String department, LocalDate startDate,
                                                                LocalDate endDate, String currency, BigDecimal cost,
                                                                List<PartView> parts, LocalDate analysisDate,
                                                                String responsiblePerson, LocalDate testDate) {
        RepairWorkOrderView workOrderView = new RepairWorkOrderView();
        workOrderView.setType(WorkOrderType.REPAIR);
        workOrderView.setDepartment(department);
        workOrderView.setStartDate(startDate);
        workOrderView.setEndDate(endDate);
        workOrderView.setCurrency(currency);
        workOrderView.setCost(cost);
        workOrderView.setParts(parts);
        workOrderView.setAnalysisDate(analysisDate);
        workOrderView.setResponsiblePerson(responsiblePerson);
        workOrderView.setTestDate(testDate);
        return workOrderView;
    }

    public static ReplacementWorkOrderView createReplacementWorkOrderView(String department, LocalDate startDate,
                                                                          LocalDate endDate, String currency,
                                                                          BigDecimal cost, List<PartView> parts,
                                                                          String factoryName,
                                                                          String factoryOrderNumber) {
        ReplacementWorkOrderView workOrderView = new ReplacementWorkOrderView();
        workOrderView.setType(WorkOrderType.REPLACEMENT);
        workOrderView.setDepartment(department);
        workOrderView.setStartDate(startDate);
        workOrderView.setEndDate(endDate);
        workOrderView.setCurrency(currency);
        workOrderView.setCost(cost);
        workOrderView.setParts(parts);
        workOrderView.setFactoryName(factoryName);
        workOrderView.setFactoryOrderNumber(factoryOrderNumber);
        return workOrderView;
    }

    public static PartView createPartView(String inventoryNumber, String name, Integer count) {
        PartView partView = new PartView();
        partView.setInventoryNumber(inventoryNumber);
        partView.setName(name);
        partView.setCount(count);
        return partView;
    }
}

