package com.laurynas.workorder.validationrest.business;

import com.laurynas.workorder.validationrest.presentation.model.RepairWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.ReplacementWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.laurynas.workorder.validationrest.business.ValidationResultCode.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationRules {

    public static <T extends WorkOrderView> Function<T, ValidationResultCode> isDepartmentValid() {
        return (workOrderView) -> StringUtils.isBlank(workOrderView.getDepartment())
                ? INVALID_DEPARTMENT
                : null;
    }

    public static <T extends WorkOrderView> Function<T, ValidationResultCode> isStartDateValid() {
        return (workOrderView) -> workOrderView.getStartDate() != null && workOrderView.getStartDate().isBefore(LocalDate.now())
                ? null
                : INVALID_START_DATE;
    }

    public static <T extends WorkOrderView> Function<T, ValidationResultCode> isEndDateValid() {
        return (workOrderView) -> workOrderView.getEndDate() != null
                && (workOrderView.getStartDate() == null || workOrderView.getEndDate().isAfter(workOrderView.getStartDate()))
                ? null
                : INVALID_END_DATE;
    }

    public static <T extends WorkOrderView> Function<T, ValidationResultCode> isCurrencyValid() {
        return (workOrderView) -> {
            try {
                Currency.getInstance(workOrderView.getCurrency());
                return null;
            } catch (NullPointerException | IllegalArgumentException ex) {
                return INVALID_CURRENCY;
            }
        };
    }

    public static <T extends WorkOrderView> Function<T, ValidationResultCode> hasPositiveCost() {
        return (workOrderView) -> workOrderView.getCost() != null && workOrderView.getCost().compareTo(BigDecimal.ZERO) > 0
                ? null
                : INVALID_COST;
    }

    public static <T extends WorkOrderView> Function<T, ValidationResultCode> hasParts() {
        return (workOrderView) -> workOrderView.getParts().size() > 0
                ? null
                : MISSING_PARTS;
    }

    public static <T extends WorkOrderView> Function<T, ValidationResultCode> allPartsWithInventoryNumbers() {
        return (workOrderView) -> hasParts().apply(workOrderView) == null
                && workOrderView.getParts().stream().allMatch(partView -> StringUtils.isNotBlank(partView.getInventoryNumber()))
                ? null
                : MISSING_INVENTORY_NUMBER_ON_PARTS;
    }

    public static Function<RepairWorkOrderView, ValidationResultCode> isAnalysisDateValid() {
        return (workOrderView) -> workOrderView.getAnalysisDate() != null
                && (workOrderView.getStartDate() == null || workOrderView.getAnalysisDate().isAfter(workOrderView.getStartDate()))
                && (workOrderView.getEndDate() == null || workOrderView.getAnalysisDate().isBefore(workOrderView.getEndDate()))
                ? null
                : INVALID_ANALYSIS_DATE;
    }

    public static Function<RepairWorkOrderView, ValidationResultCode> hasResponsiblePerson() {
        return (workOrderView) -> StringUtils.isNotBlank(workOrderView.getResponsiblePerson())
                ? null
                : MISSING_RESPONSIBLE_PERSON;
    }

    public static Function<RepairWorkOrderView, ValidationResultCode> isTestDateValid() {
        return (workOrderView) -> workOrderView.getTestDate() != null
                && (workOrderView.getAnalysisDate() == null || workOrderView.getTestDate().isAfter(workOrderView.getAnalysisDate()))
                && (workOrderView.getEndDate() == null || workOrderView.getTestDate().isBefore(workOrderView.getEndDate()))
                ? null
                : INVALID_TEST_DATE;
    }

    public static Function<ReplacementWorkOrderView, ValidationResultCode> hasFactoryName() {
        return (workOrderView) -> StringUtils.isNotBlank(workOrderView.getFactoryName())
                ? null
                : MISSING_FACTORY_NAME;
    }

    public static Function<ReplacementWorkOrderView, ValidationResultCode> isFactoryOrderNumberValid() {
        return (workOrderView) -> workOrderView.getFactoryOrderNumber() != null
                && Pattern.compile("^[a-zA-Z]{2}[0-9]{8}$").matcher(workOrderView.getFactoryOrderNumber()).matches()
                ? null
                : INVALID_FACTORY_ORDER_NUMBER;
    }

}
