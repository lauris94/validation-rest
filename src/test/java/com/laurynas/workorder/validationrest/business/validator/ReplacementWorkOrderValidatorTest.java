package com.laurynas.workorder.validationrest.business.validator;

import com.laurynas.workorder.validationrest.WorkOrderType;
import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.presentation.model.PartView;
import com.laurynas.workorder.validationrest.presentation.model.ReplacementWorkOrderView;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.laurynas.workorder.validationrest.TestObjectProvider.createPartView;
import static com.laurynas.workorder.validationrest.TestObjectProvider.createReplacementWorkOrderView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReplacementWorkOrderValidatorTest {

    private final ReplacementWorkOrderValidator validator = new ReplacementWorkOrderValidator();

    @Test
    void doesNotAcceptWhenTypeIsAnalysis() {
        WorkOrderView workOrderView = mock(WorkOrderView.class);
        when(workOrderView.getType()).thenReturn(WorkOrderType.ANALYSIS);
        assertFalse(validator.accept(workOrderView));
    }

    @Test
    void acceptWhenTypeIsReplacement() {
        WorkOrderView workOrderView = mock(WorkOrderView.class);
        when(workOrderView.getType()).thenReturn(WorkOrderType.REPLACEMENT);
        assertTrue(validator.accept(workOrderView));
    }

    @Test
    void validateInternalWhenAllRulesAreBrokenReturnsThreeCodes() {
        LocalDate startDate = LocalDate.of(2032, 5, 31);
        LocalDate endDate = LocalDate.of(2022, 5, 1);
        ReplacementWorkOrderView workOrderView = createReplacementWorkOrderView(null, startDate, endDate,
                "FAKE", BigDecimal.ZERO, List.of(), null, null);

        List<ValidationResultCode> resultCodes = validator.validateInternal(workOrderView);

        assertEquals(3, resultCodes.size());
        assertTrue(resultCodes.contains(ValidationResultCode.MISSING_FACTORY_NAME));
        assertTrue(resultCodes.contains(ValidationResultCode.INVALID_FACTORY_ORDER_NUMBER));
        assertTrue(resultCodes.contains(ValidationResultCode.MISSING_INVENTORY_NUMBER_ON_PARTS));
    }

    @Test
    void validateInternalWhenInvalidOrderNumber() {
        LocalDate startDate = LocalDate.of(2032, 5, 31);
        LocalDate endDate = LocalDate.of(2022, 5, 1);
        PartView partView = createPartView("INV123", "part name", 3);
        ReplacementWorkOrderView workOrderView = createReplacementWorkOrderView(null, startDate, endDate,
                "FAKE", BigDecimal.ZERO, List.of(partView), "test factory", "LT1234567");

        List<ValidationResultCode> resultCodes = validator.validateInternal(workOrderView);

        assertEquals(1, resultCodes.size());
        assertTrue(resultCodes.contains(ValidationResultCode.INVALID_FACTORY_ORDER_NUMBER));
    }

    @Test
    void validateInternalWhenInventoryNumberIsMissing() {
        LocalDate startDate = LocalDate.of(2032, 5, 31);
        LocalDate endDate = LocalDate.of(2022, 5, 1);
        PartView partView = createPartView(null, "part name", 3);
        ReplacementWorkOrderView workOrderView = createReplacementWorkOrderView(null, startDate, endDate,
                "FAKE", BigDecimal.ZERO, List.of(partView), "test factory", "LT12345678");

        List<ValidationResultCode> resultCodes = validator.validateInternal(workOrderView);

        assertEquals(1, resultCodes.size());
        assertTrue(resultCodes.contains(ValidationResultCode.MISSING_INVENTORY_NUMBER_ON_PARTS));
    }
}
