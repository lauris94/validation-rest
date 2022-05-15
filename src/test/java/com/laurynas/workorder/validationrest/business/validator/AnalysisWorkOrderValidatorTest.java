package com.laurynas.workorder.validationrest.business.validator;

import com.laurynas.workorder.validationrest.WorkOrderType;
import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.presentation.model.AnalysisWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.laurynas.workorder.validationrest.TestObjectProvider.createAnalysisWorkOrderView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnalysisWorkOrderValidatorTest {

    private final AnalysisWorkOrderValidator validator = new AnalysisWorkOrderValidator();

    @Test
    void doesNotAcceptWhenTypeIsRepair() {
        WorkOrderView workOrderView = mock(WorkOrderView.class);
        when(workOrderView.getType()).thenReturn(WorkOrderType.REPAIR);
        assertFalse(validator.accept(workOrderView));
    }

    @Test
    void acceptWhenTypeIsAnalysis() {
        WorkOrderView workOrderView = mock(WorkOrderView.class);
        when(workOrderView.getType()).thenReturn(WorkOrderType.ANALYSIS);
        assertTrue(validator.accept(workOrderView));
    }

    @Test
    void validateInternalReturnsEmptyResult() {
        LocalDate startDate = LocalDate.of(2032, 5, 31);
        LocalDate endDate = LocalDate.of(2022, 5, 1);
        AnalysisWorkOrderView workOrderView = createAnalysisWorkOrderView(null, startDate, endDate,
                "FAKE", BigDecimal.ZERO, List.of());
        List<ValidationResultCode> resultCodes = validator.validateInternal(workOrderView);
        assertTrue(resultCodes.isEmpty());
    }
}
