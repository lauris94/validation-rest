package com.laurynas.workorder.validationrest.business.validator;

import com.laurynas.workorder.validationrest.WorkOrderType;
import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.presentation.model.PartView;
import com.laurynas.workorder.validationrest.presentation.model.RepairWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.laurynas.workorder.validationrest.TestObjectProvider.createPartView;
import static com.laurynas.workorder.validationrest.TestObjectProvider.createRepairWorkOrderView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepairWorkOrderValidatorTest {

    private final RepairWorkOrderValidator validator = new RepairWorkOrderValidator();

    @Test
    void doesNotAcceptWhenTypeIsReplacement() {
        WorkOrderView workOrderView = mock(WorkOrderView.class);
        when(workOrderView.getType()).thenReturn(WorkOrderType.REPLACEMENT);
        assertFalse(validator.accept(workOrderView));
    }

    @Test
    void acceptWhenTypeIsRepair() {
        WorkOrderView workOrderView = mock(WorkOrderView.class);
        when(workOrderView.getType()).thenReturn(WorkOrderType.REPAIR);
        assertTrue(validator.accept(workOrderView));
    }

    @Test
    void validateInternalWhenAllRulesAreBrokenReturnsFourCodes() {
        LocalDate startDate = LocalDate.of(2032, 5, 31);
        LocalDate endDate = LocalDate.of(2022, 5, 1);
        LocalDate analysisDate = LocalDate.of(2022, 5, 1);
        LocalDate testDate = LocalDate.of(2022, 4, 20);
        RepairWorkOrderView workOrderView = createRepairWorkOrderView(null, startDate,
                endDate, "FAKE", BigDecimal.ZERO, List.of(), analysisDate, null, testDate);

        List<ValidationResultCode> resultCodes = validator.validateInternal(workOrderView);

        assertEquals(4, resultCodes.size());
        assertTrue(resultCodes.contains(ValidationResultCode.INVALID_ANALYSIS_DATE));
        assertTrue(resultCodes.contains(ValidationResultCode.MISSING_RESPONSIBLE_PERSON));
        assertTrue(resultCodes.contains(ValidationResultCode.INVALID_TEST_DATE));
        assertTrue(resultCodes.contains(ValidationResultCode.MISSING_PARTS));
    }
}
