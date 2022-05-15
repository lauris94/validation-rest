package com.laurynas.workorder.validationrest.business.validator;

import com.laurynas.workorder.validationrest.business.ValidationResultCode;
import com.laurynas.workorder.validationrest.presentation.model.WorkOrderView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class WorkOrderValidatorTest {

    @Spy
    private WorkOrderValidator<WorkOrderView> workOrderValidator;
    @Mock
    private WorkOrderView workOrderView;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void validateReturnsSumOfCommonAndInternalLists() {
        when(workOrderView.getDepartment()).thenReturn(null);
        when(workOrderView.getStartDate()).thenReturn(LocalDate.of(2032, 5, 31));
        when(workOrderView.getEndDate()).thenReturn(LocalDate.of(2022, 5, 1));
        when(workOrderView.getCurrency()).thenReturn("FAKE");
        when(workOrderView.getCost()).thenReturn(BigDecimal.ZERO);
        when(workOrderValidator.validateInternal(workOrderView)).thenReturn(List.of(ValidationResultCode.MISSING_FACTORY_NAME));

        List<ValidationResultCode> resultCodes = workOrderValidator.validate(workOrderView);

        assertEquals(6, resultCodes.size());
        assertTrue(resultCodes.contains(ValidationResultCode.INVALID_DEPARTMENT));
        assertTrue(resultCodes.contains(ValidationResultCode.INVALID_START_DATE));
        assertTrue(resultCodes.contains(ValidationResultCode.INVALID_END_DATE));
        assertTrue(resultCodes.contains(ValidationResultCode.INVALID_CURRENCY));
        assertTrue(resultCodes.contains(ValidationResultCode.INVALID_COST));
        assertTrue(resultCodes.contains(ValidationResultCode.MISSING_FACTORY_NAME));
    }
}
